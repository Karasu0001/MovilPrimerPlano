package com.healthsync.app.network.repository

import com.healthsync.app.network.ApiException
import com.healthsync.app.network.ApiClient
import com.healthsync.app.network.NetworkResult
import com.healthsync.app.network.response.ReadingsResponse
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.serializer
import kotlinx.coroutines.delay

class DashboardRepository {

    suspend fun getReadings(patientId: String, token: String): NetworkResult<ReadingsResponse> {
        return try {
            val response = ApiClient.get(
                "api/v1/dashboard/$patientId/readings",
                ReadingsResponse.serializer(),
                token
            )
            NetworkResult.Success(response)
        } catch (e: ApiException) {
            when (e.httpCode) {
                401 -> NetworkResult.Error("Sesión expirada, iniciá sesión nuevamente", 401)
                404 -> NetworkResult.Error("No se encontraron lecturas para este paciente", 404)
                else -> NetworkResult.Error(extractErrorMessage(e.rawBody) ?: "Error del servidor")
            }
        } catch (e: java.net.UnknownHostException) {
            NetworkResult.Error("Sin conexión a internet. Verificá tu red Wi-Fi o datos móviles.")
        } catch (e: java.net.SocketTimeoutException) {
            NetworkResult.Error("Conectando con el servidor, esto puede tardar unos segundos...")
        } catch (e: java.io.IOException) {
            NetworkResult.Error("Error de conexión. Reintentá en unos instantes.")
        } catch (e: Exception) {
            NetworkResult.Error("Error inesperado. Intentá nuevamente.")
        }
    }

    private fun extractErrorMessage(rawBody: String): String? {
        return try {
            val jsonElement = ApiClient.json.parseToJsonElement(rawBody)
            when (jsonElement) {
                is JsonObject -> {
                    val error = jsonElement["error"] ?: return null
                    when (error) {
                        is JsonObject -> error["message"]?.jsonPrimitive?.content
                        is kotlinx.serialization.json.JsonPrimitive -> error.content
                        else -> null
                    }
                }
                else -> null
            }
        } catch (_: Exception) {
            null
        }
    }
}
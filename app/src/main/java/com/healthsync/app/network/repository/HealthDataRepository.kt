package com.healthsync.app.network.repository

import com.healthsync.app.network.ApiException
import com.healthsync.app.network.ApiClient
import com.healthsync.app.network.NetworkResult
import com.healthsync.app.network.RateLimiter
import com.healthsync.app.network.request.BatchHealthDataRequest
import com.healthsync.app.network.request.BatchDataPoint
import com.healthsync.app.network.response.BatchHealthDataResponse
import com.healthsync.app.network.response.PatientInfoResponse
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.serializer
import kotlinx.coroutines.delay

class HealthDataRepository {

    private val rateLimiter = RateLimiter(maxRequests = 30, windowMillis = 60_000)

    suspend fun batchHealthData(
        patientCode: String,
        data: List<BatchDataPoint>
    ): NetworkResult<BatchHealthDataResponse> {
        return try {
            rateLimiter.acquire()
            val request = BatchHealthDataRequest(patientCode, data)
            val response = ApiClient.post(
                "api/v1/health-data/batch",
                request,
                BatchHealthDataRequest.serializer(),
                BatchHealthDataResponse.serializer()
            )
            NetworkResult.Success(response)
        } catch (e: ApiException) {
            when (e.httpCode) {
                404 -> NetworkResult.Error("No se encontró un paciente con ese código", 404)
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

    suspend fun getPatientInfo(patientCode: String): NetworkResult<PatientInfoResponse> {
        return try {
            val response = ApiClient.get(
                "api/v1/health-data/patient-info/$patientCode",
                PatientInfoResponse.serializer()
            )
            NetworkResult.Success(response)
        } catch (e: ApiException) {
            when (e.httpCode) {
                404 -> NetworkResult.Error("No se encontró un paciente con ese código", 404)
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
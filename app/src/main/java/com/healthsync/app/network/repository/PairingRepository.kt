package com.healthsync.app.network.repository

import com.healthsync.app.network.ApiException
import com.healthsync.app.network.ApiClient
import com.healthsync.app.network.NetworkResult
import com.healthsync.app.network.request.LinkDeviceRequest
import com.healthsync.app.network.request.ValidateCodeRequest
import com.healthsync.app.network.response.LinkDeviceResponse
import com.healthsync.app.network.response.ValidateCodeResponse
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.coroutines.delay

class PairingRepository {

    suspend fun validateCode(code: String): NetworkResult<ValidateCodeResponse> {
        return try {
            val response = ApiClient.post(
                "api/v1/patients/validate-code",
                ValidateCodeRequest(code),
                ValidateCodeRequest.serializer(),
                ValidateCodeResponse.serializer()
            )
            if (response.isValid) {
                NetworkResult.Success(response)
            } else {
                NetworkResult.Error("Código inválido o expirado", 400)
            }
        } catch (e: ApiException) {
            when (e.httpCode) {
                400 -> NetworkResult.Error("Código inválido o expirado", 400)
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

    suspend fun linkDevice(code: String, serialNumber: String): NetworkResult<LinkDeviceResponse> {
        return try {
            val response = ApiClient.post(
                "api/v1/devices/link",
                LinkDeviceRequest(code, serialNumber),
                LinkDeviceRequest.serializer(),
                LinkDeviceResponse.serializer()
            )
            if (response.success == true) {
                NetworkResult.Success(response)
            } else {
                NetworkResult.Error(response.message ?: "Error al vincular dispositivo", 400)
            }
        } catch (e: ApiException) {
            when (e.httpCode) {
                400 -> {
                    val msg = extractErrorMessage(e.rawBody)
                    NetworkResult.Error(
                        if (msg == "Invalid code.") "El código ya no es válido, generá uno nuevo"
                        else msg ?: "El código ya no es válido, generá uno nuevo"
                    )
                }
                else -> NetworkResult.Error(extractErrorMessage(e.rawBody) ?: "Error del servidor")
            }
        } catch (e: java.net.UnknownHostException) {
            NetworkResult.Error("Sin conexión a internet. Verificá tu red Wi-Fi o datos móviles.")
        } catch (e: java.net.SocketTimeoutException) {
            NetworkResult.Error("Conectando con el servidor, esto puede tardar unos segundos...")
        } catch (e: java.io.IOException) {
            NetworkResult.Error("Error de conexión. Reintentá en unos instantes.")
        } catch (e: Exception) {
            NetworkResult.Error("Error inesperado al vincular. Intentá nuevamente.")
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
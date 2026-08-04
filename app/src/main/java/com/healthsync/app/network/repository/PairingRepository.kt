package com.healthsync.app.network.repository

import com.healthsync.app.network.ApiClient
import com.healthsync.app.network.NetworkResult
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

class PairingRepository {

    suspend fun validateCode(code: String): NetworkResult<ValidateCodeResult> {
        return try {
            val body = mapOf("code" to code)
            val response = ApiClient.post("api/v1/patients/validate-code", body)
            val valid = getBoolean(response, "valid")
            val message = getString(response, "message")
            val patientCode = getString(response, "patientCode")
            if (valid) {
                NetworkResult.Success(ValidateCodeResult(valid, message, patientCode))
            } else {
                NetworkResult.Error(message.ifEmpty { "Código inválido" }, 400)
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

    suspend fun linkDevice(code: String, serialNumber: String): NetworkResult<LinkDeviceResult> {
        return try {
            val body = mapOf("code" to code, "serialNumber" to serialNumber)
            val response = ApiClient.post("api/v1/devices/link", body)
            val success = getBoolean(response, "success")
            val message = getString(response, "message")
            val deviceId = getString(response, "deviceId")
            if (success) {
                NetworkResult.Success(LinkDeviceResult(success, message, deviceId))
            } else {
                NetworkResult.Error(message.ifEmpty { "Error al vincular dispositivo" }, 400)
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

    private fun getBoolean(json: JsonObject, key: String): Boolean {
        return (json[key] as? JsonPrimitive)?.content?.toBoolean() ?: false
    }

    private fun getString(json: JsonObject, key: String): String {
        return (json[key] as? JsonPrimitive)?.content ?: ""
    }
}

data class ValidateCodeResult(
    val valid: Boolean,
    val message: String,
    val patientCode: String?
)

data class LinkDeviceResult(
    val success: Boolean,
    val message: String,
    val deviceId: String?
)
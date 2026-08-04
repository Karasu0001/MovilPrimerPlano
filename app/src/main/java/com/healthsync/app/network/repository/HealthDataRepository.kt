package com.healthsync.app.network.repository

import com.healthsync.app.network.ApiClient
import com.healthsync.app.network.NetworkResult
import com.healthsync.app.network.RateLimiter
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

class HealthDataRepository {

    private val rateLimiter = RateLimiter(maxRequests = 30, windowMillis = 60_000)

    suspend fun batchHealthData(
        patientCode: String,
        data: List<Map<String, Any>>
    ): NetworkResult<BatchHealthDataResult> {
        return try {
            rateLimiter.acquire()
            val body = mapOf(
                "patientCode" to patientCode,
                "data" to data
            )
            val response = ApiClient.post("api/v1/health-data/batch", body)
            val success = getBoolean(response, "success")
            val recordsProcessed = getInt(response, "recordsProcessed")
            val message = getString(response, "message")
            if (success) {
                NetworkResult.Success(BatchHealthDataResult(success, recordsProcessed, message))
            } else {
                NetworkResult.Error(message.ifEmpty { "Error al enviar datos" }, 400)
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

    suspend fun getPatientInfo(patientCode: String): NetworkResult<PatientInfoResult> {
        return try {
            val response = ApiClient.get("api/v1/health-data/patient-info/$patientCode")
            NetworkResult.Success(
                PatientInfoResult(
                    patientCode = getString(response, "patientCode"),
                    fullName = getString(response, "fullName"),
                    deviceConnected = getBoolean(response, "deviceConnected"),
                    lastSync = getString(response, "lastSync").takeIf { it.isNotEmpty() },
                    heartRate = getIntOrNull(response, "heartRate"),
                    oxygenSaturation = getIntOrNull(response, "oxygenSaturation"),
                    activityLevel = getIntOrNull(response, "activityLevel")
                )
            )
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

    private fun getBoolean(json: JsonObject, key: String): Boolean {
        return (json[key] as? JsonPrimitive)?.content?.toBoolean() ?: false
    }

    private fun getString(json: JsonObject, key: String): String {
        return (json[key] as? JsonPrimitive)?.content ?: ""
    }

    private fun getInt(json: JsonObject, key: String): Int {
        return (json[key] as? JsonPrimitive)?.content?.toIntOrNull() ?: 0
    }

    private fun getIntOrNull(json: JsonObject, key: String): Int? {
        return (json[key] as? JsonPrimitive)?.content?.toIntOrNull()
    }
}

data class BatchHealthDataResult(
    val success: Boolean,
    val recordsProcessed: Int,
    val message: String?
)

data class PatientInfoResult(
    val patientCode: String,
    val fullName: String,
    val deviceConnected: Boolean,
    val lastSync: String?,
    val heartRate: Int?,
    val oxygenSaturation: Int?,
    val activityLevel: Int?
)
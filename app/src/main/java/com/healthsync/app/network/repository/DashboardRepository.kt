package com.healthsync.app.network.repository

import com.healthsync.app.network.ApiException
import com.healthsync.app.network.ApiClient
import com.healthsync.app.network.NetworkResult
import com.healthsync.app.data.local.HealthSyncDb
import com.healthsync.app.data.local.PatientEntity
import com.healthsync.app.data.local.ReadingEntity
import com.healthsync.app.data.local.AlertEntity
import com.healthsync.app.network.extractErrorMessage
import com.healthsync.app.network.response.AlertDto
import com.healthsync.app.network.response.CreatePatientRequest
import com.healthsync.app.network.response.DashboardSummaryResponse
import com.healthsync.app.network.response.GenerateWearableCodeResponse
import com.healthsync.app.network.response.PatientDetailResponse
import com.healthsync.app.network.response.ReadingsResponse
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.serializer

class DashboardRepository {

    suspend fun getDashboard(token: String): NetworkResult<DashboardSummaryResponse> {
        return try {
            val response = ApiClient.get(
                "api/v1/dashboard",
                DashboardSummaryResponse.serializer(),
                token
            )
            val now = System.currentTimeMillis()
            HealthSyncDb.patientDao().upsertAll(response.patients.map { p ->
                PatientEntity(
                    patientId = p.patientId,
                    name = p.name,
                    age = null,
                    gender = null,
                    notes = null,
                    hasDevice = p.hasDevice,
                    deviceSerialNumber = null,
                    lastHeartRate = p.lastHeartRate,
                    lastOxygen = p.lastOxygen,
                    lastActivity = p.lastActivity,
                    lastReadingAt = p.lastReadingAt,
                    updatedAt = now
                )
            })
            NetworkResult.Success(response)
        } catch (e: ApiException) {
            when (e.httpCode) {
                401 -> NetworkResult.Error("Sesi\u00f3n expirada, inici\u00e1 sesi\u00f3n nuevamente", 401)
                else -> NetworkResult.Error(extractErrorMessage(e.rawBody) ?: "Error del servidor")
            }
        } catch (e: java.net.UnknownHostException) {
            NetworkResult.Error("Sin conexi\u00f3n a internet. Verific\u00e1 tu red Wi-Fi o datos m\u00f3viles.")
        } catch (e: java.net.SocketTimeoutException) {
            NetworkResult.Error("Conectando con el servidor, esto puede tardar unos segundos...")
        } catch (e: java.io.IOException) {
            NetworkResult.Error("Error de conexi\u00f3n. Reintent\u00e1 en unos instantes.")
        } catch (e: Exception) {
            NetworkResult.Error("Error inesperado. Intent\u00e1 nuevamente.")
        }
    }

    suspend fun getPatientDetail(patientId: String, token: String): NetworkResult<PatientDetailResponse> {
        return try {
            val response = ApiClient.get(
                "api/v1/dashboard/$patientId",
                PatientDetailResponse.serializer(),
                token
            )
            HealthSyncDb.patientDao().upsert(
                PatientEntity(
                    patientId = response.patientId,
                    name = response.name,
                    age = response.age,
                    gender = response.gender,
                    notes = response.notes,
                    hasDevice = response.hasDevice,
                    deviceSerialNumber = null,
                    lastHeartRate = response.lastHeartRate,
                    lastOxygen = response.lastOxygen,
                    lastActivity = response.lastActivity,
                    lastReadingAt = response.lastReadingAt,
                    updatedAt = System.currentTimeMillis()
                )
            )
            NetworkResult.Success(response)
        } catch (e: ApiException) {
            when (e.httpCode) {
                401 -> NetworkResult.Error("Sesi\u00f3n expirada, inici\u00e1 sesi\u00f3n nuevamente", 401)
                404 -> NetworkResult.Error("Paciente no encontrado", 404)
                else -> NetworkResult.Error(extractErrorMessage(e.rawBody) ?: "Error del servidor")
            }
        } catch (e: java.net.UnknownHostException) {
            NetworkResult.Error("Sin conexi\u00f3n a internet. Verific\u00e1 tu red Wi-Fi o datos m\u00f3viles.")
        } catch (e: java.net.SocketTimeoutException) {
            NetworkResult.Error("Conectando con el servidor, esto puede tardar unos segundos...")
        } catch (e: java.io.IOException) {
            NetworkResult.Error("Error de conexi\u00f3n. Reintent\u00e1 en unos instantes.")
        } catch (e: Exception) {
            NetworkResult.Error("Error inesperado. Intent\u00e1 nuevamente.")
        }
    }

    suspend fun getReadings(patientId: String, token: String): NetworkResult<ReadingsResponse> {
        return try {
            val response = ApiClient.get(
                "api/v1/dashboard/$patientId/readings",
                ReadingsResponse.serializer(),
                token
            )
            val now = System.currentTimeMillis()
            HealthSyncDb.readingDao().upsertAll(response.readings.map { r ->
                ReadingEntity(
                    patientId = patientId,
                    timestamp = r.timestamp ?: "",
                    heartRate = r.heartRate,
                    oxygen = r.oxygen,
                    activity = r.activity,
                    recordedAt = now
                )
            })
            NetworkResult.Success(response)
        } catch (e: ApiException) {
            when (e.httpCode) {
                401 -> NetworkResult.Error("Sesi\u00f3n expirada, inici\u00e1 sesi\u00f3n nuevamente", 401)
                404 -> NetworkResult.Error("No se encontraron lecturas para este paciente", 404)
                else -> NetworkResult.Error(extractErrorMessage(e.rawBody) ?: "Error del servidor")
            }
        } catch (e: java.net.UnknownHostException) {
            NetworkResult.Error("Sin conexi\u00f3n a internet. Verific\u00e1 tu red Wi-Fi o datos m\u00f3viles.")
        } catch (e: java.net.SocketTimeoutException) {
            NetworkResult.Error("Conectando con el servidor, esto puede tardar unos segundos...")
        } catch (e: java.io.IOException) {
            NetworkResult.Error("Error de conexi\u00f3n. Reintent\u00e1 en unos instantes.")
        } catch (e: Exception) {
            NetworkResult.Error("Error inesperado. Intent\u00e1 nuevamente.")
        }
    }

    suspend fun generateWearableCode(patientId: String, token: String): NetworkResult<GenerateWearableCodeResponse> {
        return try {
            val response = ApiClient.post(
                "api/v1/patients/$patientId/generate-wearable-code",
                JsonObject(emptyMap()),
                JsonObject.serializer(),
                GenerateWearableCodeResponse.serializer(),
                token
            )
            NetworkResult.Success(response)
        } catch (e: ApiException) {
            when (e.httpCode) {
                401 -> NetworkResult.Error("Sesi\u00f3n expirada, inici\u00e1 sesi\u00f3n nuevamente", 401)
                else -> NetworkResult.Error(extractErrorMessage(e.rawBody) ?: "Error del servidor")
            }
        } catch (e: java.net.UnknownHostException) {
            NetworkResult.Error("Sin conexi\u00f3n a internet. Verific\u00e1 tu red Wi-Fi o datos m\u00f3viles.")
        } catch (e: java.net.SocketTimeoutException) {
            NetworkResult.Error("Conectando con el servidor, esto puede tardar unos segundos...")
        } catch (e: java.io.IOException) {
            NetworkResult.Error("Error de conexi\u00f3n. Reintent\u00e1 en unos instantes.")
        } catch (e: Exception) {
            NetworkResult.Error("Error inesperado. Intent\u00e1 nuevamente.")
        }
    }

    suspend fun createPatient(request: CreatePatientRequest, token: String): NetworkResult<PatientDetailResponse> {
        return try {
            val response = ApiClient.post(
                "api/v1/patients",
                request,
                CreatePatientRequest.serializer(),
                PatientDetailResponse.serializer(),
                token
            )
            NetworkResult.Success(response)
        } catch (e: ApiException) {
            when (e.httpCode) {
                401 -> NetworkResult.Error("Sesi\u00f3n expirada, inici\u00e1 sesi\u00f3n nuevamente", 401)
                else -> NetworkResult.Error(extractErrorMessage(e.rawBody) ?: "Error del servidor")
            }
        } catch (e: java.net.UnknownHostException) {
            NetworkResult.Error("Sin conexi\u00f3n a internet. Verific\u00e1 tu red Wi-Fi o datos m\u00f3viles.")
        } catch (e: java.net.SocketTimeoutException) {
            NetworkResult.Error("Conectando con el servidor, esto puede tardar unos segundos...")
        } catch (e: java.io.IOException) {
            NetworkResult.Error("Error de conexi\u00f3n. Reintent\u00e1 en unos instantes.")
        } catch (e: Exception) {
            NetworkResult.Error("Error inesperado. Intent\u00e1 nuevamente.")
        }
    }

    suspend fun getAlerts(token: String): NetworkResult<List<AlertDto>> {
        return try {
            val response = ApiClient.get(
                "api/v1/alerts",
                kotlinx.serialization.builtins.ListSerializer(AlertDto.serializer()),
                token
            )
            HealthSyncDb.alertDao().upsertAll(response.map { a ->
                AlertEntity(
                    id = a.id,
                    patientId = a.patientId,
                    patientName = a.patientName,
                    type = a.type,
                    message = a.message,
                    severity = a.severity,
                    isRead = a.isRead,
                    createdAt = a.createdAt
                )
            })
            NetworkResult.Success(response)
        } catch (e: ApiException) {
            when (e.httpCode) {
                401 -> NetworkResult.Error("Sesi\u00f3n expirada, inici\u00e1 sesi\u00f3n nuevamente", 401)
                else -> NetworkResult.Error(extractErrorMessage(e.rawBody) ?: "Error del servidor")
            }
        } catch (e: java.net.UnknownHostException) {
            NetworkResult.Error("Sin conexi\u00f3n a internet. Verific\u00e1 tu red Wi-Fi o datos m\u00f3viles.")
        } catch (e: java.net.SocketTimeoutException) {
            NetworkResult.Error("Conectando con el servidor, esto puede tardar unos segundos...")
        } catch (e: java.io.IOException) {
            NetworkResult.Error("Error de conexi\u00f3n. Reintent\u00e1 en unos instantes.")
        } catch (e: Exception) {
            NetworkResult.Error("Error inesperado. Intent\u00e1 nuevamente.")
        }
    }

    suspend fun getAlertsForPatient(patientId: String, token: String): NetworkResult<List<AlertDto>> {
        return try {
            val response = ApiClient.get(
                "api/v1/alerts/$patientId",
                kotlinx.serialization.builtins.ListSerializer(AlertDto.serializer()),
                token
            )
            HealthSyncDb.alertDao().upsertAll(response.map { a ->
                AlertEntity(
                    id = a.id,
                    patientId = a.patientId,
                    patientName = a.patientName,
                    type = a.type,
                    message = a.message,
                    severity = a.severity,
                    isRead = a.isRead,
                    createdAt = a.createdAt
                )
            })
            NetworkResult.Success(response)
        } catch (e: ApiException) {
            when (e.httpCode) {
                401 -> NetworkResult.Error("Sesi\u00f3n expirada, inici\u00e1 sesi\u00f3n nuevamente", 401)
                else -> NetworkResult.Error(extractErrorMessage(e.rawBody) ?: "Error del servidor")
            }
        } catch (e: java.net.UnknownHostException) {
            NetworkResult.Error("Sin conexi\u00f3n a internet. Verific\u00e1 tu red Wi-Fi o datos m\u00f3viles.")
        } catch (e: java.net.SocketTimeoutException) {
            NetworkResult.Error("Conectando con el servidor, esto puede tardar unos segundos...")
        } catch (e: java.io.IOException) {
            NetworkResult.Error("Error de conexi\u00f3n. Reintent\u00e1 en unos instantes.")
        } catch (e: Exception) {
            NetworkResult.Error("Error inesperado. Intent\u00e1 nuevamente.")
        }
    }

    suspend fun deleteAlert(alertId: String, token: String): NetworkResult<Unit> {
        return try {
            ApiClient.delete(
                "api/v1/alerts/$alertId",
                JsonObject.serializer(),
                token
            )
            NetworkResult.Success(Unit)
        } catch (e: ApiException) {
            when (e.httpCode) {
                401 -> NetworkResult.Error("Sesi\u00f3n expirada, inici\u00e1 sesi\u00f3n nuevamente", 401)
                else -> NetworkResult.Error(extractErrorMessage(e.rawBody) ?: "Error del servidor")
            }
        } catch (e: java.net.UnknownHostException) {
            NetworkResult.Error("Sin conexi\u00f3n a internet. Verific\u00e1 tu red Wi-Fi o datos m\u00f3viles.")
        } catch (e: java.net.SocketTimeoutException) {
            NetworkResult.Error("Conectando con el servidor, esto puede tardar unos segundos...")
        } catch (e: java.io.IOException) {
            NetworkResult.Error("Error de conexi\u00f3n. Reintent\u00e1 en unos instantes.")
        } catch (e: Exception) {
            NetworkResult.Error("Error inesperado. Intent\u00e1 nuevamente.")
        }
    }

}
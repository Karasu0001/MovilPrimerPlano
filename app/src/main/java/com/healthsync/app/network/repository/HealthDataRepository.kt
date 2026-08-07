package com.healthsync.app.network.repository

import com.healthsync.app.network.ApiException
import com.healthsync.app.network.ApiClient
import com.healthsync.app.network.NetworkResult
import com.healthsync.app.network.RateLimiter
import com.healthsync.app.data.local.HealthSyncDb
import com.healthsync.app.data.local.PatientEntity
import com.healthsync.app.data.local.ReadingEntity
import com.healthsync.app.network.request.BatchHealthDataRequest
import com.healthsync.app.network.request.BatchDataPoint
import com.healthsync.app.network.response.BatchHealthDataResponse
import com.healthsync.app.network.extractErrorMessage
import com.healthsync.app.network.response.PatientInfoResponse
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
            val now = System.currentTimeMillis()
            HealthSyncDb.readingDao().upsertAll(data.map { dp ->
                ReadingEntity(
                    patientId = patientCode,
                    timestamp = dp.timestamp,
                    heartRate = dp.heartRate,
                    oxygen = dp.oxygen,
                    activity = dp.activity,
                    recordedAt = now
                )
            })
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
            val now = System.currentTimeMillis()
            HealthSyncDb.patientDao().upsert(
                PatientEntity(
                    patientId = response.patientCode ?: patientCode,
                    name = response.name ?: "",
                    age = null,
                    gender = null,
                    notes = null,
                    hasDevice = response.deviceSerialNumber != null,
                    deviceSerialNumber = response.deviceSerialNumber,
                    lastHeartRate = response.lastHeartRate,
                    lastOxygen = response.lastOxygen,
                    lastActivity = response.lastActivity,
                    lastReadingAt = response.lastReadingAt,
                    updatedAt = now
                )
            )
            if (response.lastHeartRate != null || response.lastOxygen != null || response.lastActivity != null) {
                HealthSyncDb.readingDao().upsert(
                    ReadingEntity(
                        patientId = response.patientCode ?: patientCode,
                        timestamp = response.lastReadingAt ?: "",
                        heartRate = response.lastHeartRate,
                        oxygen = response.lastOxygen,
                        activity = response.lastActivity,
                        recordedAt = now
                    )
                )
            }
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

}
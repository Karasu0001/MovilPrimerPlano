package com.healthsync.app.network.request

import kotlinx.serialization.Serializable

@Serializable
data class BatchHealthDataRequest(
    val patientCode: String,
    val data: List<BatchDataPoint>
)

@Serializable
data class BatchDataPoint(
    val heartRate: Double,
    val oxygen: Double,
    val activity: Double,
    val timestamp: String
)
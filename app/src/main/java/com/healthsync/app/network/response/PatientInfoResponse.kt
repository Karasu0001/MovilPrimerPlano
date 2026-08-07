package com.healthsync.app.network.response

import kotlinx.serialization.Serializable

@Serializable
data class PatientInfoResponse(
    val patientCode: String? = null,
    val name: String? = null,
    val deviceSerialNumber: String? = null,
    val lastHeartRate: Double? = null,
    val lastOxygen: Double? = null,
    val lastActivity: Double? = null,
    val lastReadingAt: String? = null
)
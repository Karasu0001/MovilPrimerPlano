package com.healthsync.app.network.response

import kotlinx.serialization.Serializable

@Serializable
data class PatientInfoResponse(
    val patientCode: String? = null,
    val name: String? = null,
    val deviceSerialNumber: String? = null,
    val lastHeartRate: Int? = null,
    val lastOxygen: Int? = null,
    val lastActivity: Int? = null,
    val lastReadingAt: String? = null
)
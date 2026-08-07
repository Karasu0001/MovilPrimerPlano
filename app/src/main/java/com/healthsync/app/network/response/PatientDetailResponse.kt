package com.healthsync.app.network.response

import kotlinx.serialization.Serializable

@Serializable
data class PatientDetailResponse(
    val patientId: String = "",
    val name: String = "",
    val age: Int = 0,
    val gender: String = "",
    val notes: String = "",
    val hasDevice: Boolean = false,
    val lastHeartRate: Double? = null,
    val lastOxygen: Double? = null,
    val lastActivity: Double? = null,
    val lastReadingAt: String? = null
)
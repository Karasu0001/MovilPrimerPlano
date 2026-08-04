package com.healthsync.app.network.response

import kotlinx.serialization.Serializable

@Serializable
data class PatientInfoResponse(
    val patientCode: String,
    val fullName: String,
    val patientName: String? = null,
    val deviceConnected: Boolean,
    val lastSync: String? = null,
    val heartRate: Int? = null,
    val oxygenSaturation: Int? = null,
    val activityLevel: Int? = null
)
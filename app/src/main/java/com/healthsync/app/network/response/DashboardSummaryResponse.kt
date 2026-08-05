package com.healthsync.app.network.response

import kotlinx.serialization.Serializable

@Serializable
data class DashboardSummaryResponse(
    val totalPatients: Int = 0,
    val activeAlerts: Int = 0,
    val patientsWithDevice: Int = 0,
    val patients: List<PatientSummaryDto> = emptyList()
)

@Serializable
data class PatientSummaryDto(
    val patientId: String = "",
    val name: String = "",
    val lastHeartRate: Int? = null,
    val lastOxygen: Int? = null,
    val lastActivity: Int? = null,
    val lastReadingAt: String? = null,
    val hasDevice: Boolean = false,
    val activeAlerts: Int = 0
)
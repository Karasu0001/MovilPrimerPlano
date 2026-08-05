package com.healthsync.app.network.response

import kotlinx.serialization.Serializable

@Serializable
data class AlertDto(
    val id: String = "",
    val patientId: String = "",
    val patientName: String = "",
    val type: String = "",
    val message: String = "",
    val severity: Int = 0,
    val isRead: Boolean = false,
    val createdAt: String = ""
)
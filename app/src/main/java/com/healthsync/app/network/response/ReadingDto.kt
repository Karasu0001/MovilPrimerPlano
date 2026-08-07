package com.healthsync.app.network.response

import kotlinx.serialization.Serializable

@Serializable
data class ReadingDto(
    val timestamp: String? = null,
    val heartRate: Double? = null,
    val oxygen: Double? = null,
    val activity: Double? = null
)
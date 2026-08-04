package com.healthsync.app.network.response

import kotlinx.serialization.Serializable

@Serializable
data class ReadingDto(
    val timestamp: String? = null,
    val heartRate: Int? = null,
    val oxygen: Int? = null,
    val activity: Int? = null
)
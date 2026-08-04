package com.healthsync.app.network.response

import kotlinx.serialization.Serializable

@Serializable
data class ReadingsResponse(
    val patientId: String? = null,
    val readings: List<ReadingDto> = emptyList()
)
package com.healthsync.app.network.response

import kotlinx.serialization.Serializable

@Serializable
data class BatchHealthDataResponse(
    val status: String? = null,
    val alertsTriggered: Int? = null
)
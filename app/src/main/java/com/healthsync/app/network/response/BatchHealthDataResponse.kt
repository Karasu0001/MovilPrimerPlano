package com.healthsync.app.network.response

import kotlinx.serialization.Serializable

@Serializable
data class BatchHealthDataResponse(
    val success: Boolean? = null,
    val recordsProcessed: Int? = null,
    val message: String? = null
)
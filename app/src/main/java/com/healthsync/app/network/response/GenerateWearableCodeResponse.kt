package com.healthsync.app.network.response

import kotlinx.serialization.Serializable

@Serializable
data class GenerateWearableCodeResponse(
    val code: String = "",
    val expiresInSeconds: Int = 0
)
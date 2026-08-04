package com.healthsync.app.network.request

import kotlinx.serialization.Serializable

@Serializable
data class ValidateCodeRequest(
    val code: String
)
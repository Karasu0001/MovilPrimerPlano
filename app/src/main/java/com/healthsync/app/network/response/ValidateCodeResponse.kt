package com.healthsync.app.network.response

import kotlinx.serialization.Serializable

@Serializable
data class ValidateCodeResponse(
    val isValid: Boolean,
    val patientId: String? = null,
    val patientName: String? = null
)
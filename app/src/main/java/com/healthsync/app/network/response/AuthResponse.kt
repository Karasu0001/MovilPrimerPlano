package com.healthsync.app.network.response

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String? = null,
    val caregiver: CaregiverDto? = null
)
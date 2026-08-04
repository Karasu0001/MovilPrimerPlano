package com.healthsync.app.network.response

import kotlinx.serialization.Serializable

@Serializable
data class CaregiverDto(
    val id: String? = null,
    val name: String? = null,
    val lastname: String? = null,
    val phone: String? = null,
    val imageUrl: String? = null,
    val email: String? = null,
    val plan: String? = null
)
package com.healthsync.app.network.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val name: String,
    val lastname: String,
    val phone: String,
    val imageUrl: String = "",
    val email: String,
    val password: String,
    val plan: String = "free"
)
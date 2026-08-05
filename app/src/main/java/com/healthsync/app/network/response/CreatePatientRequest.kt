package com.healthsync.app.network.response

import kotlinx.serialization.Serializable

@Serializable
data class CreatePatientRequest(
    val name: String,
    val age: Int,
    val gender: String,
    val notes: String = ""
)
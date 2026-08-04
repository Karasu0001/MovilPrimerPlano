package com.healthsync.app.network.response

import kotlinx.serialization.Serializable

@Serializable
data class LinkDeviceResponse(
    val linked: Boolean = false,
    val serialNumber: String? = null,
    val patientId: String? = null
)
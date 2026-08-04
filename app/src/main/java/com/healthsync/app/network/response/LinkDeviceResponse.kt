package com.healthsync.app.network.response

import kotlinx.serialization.Serializable

@Serializable
data class LinkDeviceResponse(
    val success: Boolean? = null,
    val message: String? = null,
    val deviceId: String? = null
)
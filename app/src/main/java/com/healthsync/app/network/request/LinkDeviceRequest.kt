package com.healthsync.app.network.request

import kotlinx.serialization.Serializable

@Serializable
data class LinkDeviceRequest(
    val code: String,
    val serialNumber: String
)
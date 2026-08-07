package com.healthsync.app.network

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive

fun extractErrorMessage(rawBody: String): String? {
    return try {
        val trimmed = rawBody.trim()
        if (!trimmed.startsWith("{") && !trimmed.startsWith("[")) {
            return trimmed.ifBlank { null }
        }
        val jsonElement = ApiClient.json.parseToJsonElement(trimmed)
        if (jsonElement !is JsonObject) return null
        when (val error = jsonElement["error"] ?: return null) {
            is JsonPrimitive -> error.contentOrNull
            is JsonObject -> {
                // Shape real: {"error":{"message":"...","errors":{"Code":["Invalid code."]}}}
                val fieldErrors = error["errors"] as? JsonObject
                val specific = fieldErrors?.values?.firstOrNull()
                    ?.let { it as? kotlinx.serialization.json.JsonArray }
                    ?.firstOrNull()?.jsonPrimitive?.contentOrNull
                specific ?: error["message"]?.jsonPrimitive?.contentOrNull
            }
            else -> null
        }
    } catch (_: Exception) {
        null
    }
}

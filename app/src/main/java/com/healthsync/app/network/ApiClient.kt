package com.healthsync.app.network

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object ApiClient {

    private const val BASE_URL = "https://api-dialitech-core-v2.onrender.com/"
    private const val CONNECT_TIMEOUT_SECONDS = 60L
    private const val READ_TIMEOUT_SECONDS = 60L
    private const val WRITE_TIMEOUT_SECONDS = 60L

    val json: Json = Json { ignoreUnknownKeys = true }

    private val mediaType = "application/json".toMediaType()

    private val client: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.NONE

        OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(logging)
            .addInterceptor(RetryInterceptor(maxRetries = 2))
            .build()
    }

    suspend fun get(path: String): JsonObject {
        val request = Request.Builder()
            .url(BASE_URL + path)
            .build()
        val response = client.newCall(request).execute()
        if (!response.isSuccessful) {
            throw java.io.IOException("HTTP ${response.code}")
        }
        val body = response.body?.string() ?: throw java.io.IOException("Empty body")
        return json.parseToJsonElement(body).jsonObject
    }

    suspend fun post(path: String, body: Map<String, Any>): JsonObject {
        val jsonObject = body.mapValues { (_, value) ->
            when (value) {
                is Boolean -> JsonPrimitive(value)
                is Number -> JsonPrimitive(value)
                is String -> JsonPrimitive(value)
                else -> JsonPrimitive(value.toString())
            }
        }
        val requestBody = JsonObject(jsonObject).toString().toRequestBody(mediaType)
        val request = Request.Builder()
            .url(BASE_URL + path)
            .post(requestBody)
            .build()
        val response = client.newCall(request).execute()
        if (!response.isSuccessful) {
            throw java.io.IOException("HTTP ${response.code}")
        }
        val responseBody = response.body?.string() ?: throw java.io.IOException("Empty body")
        return json.parseToJsonElement(responseBody).jsonObject
    }
}
package com.healthsync.app.network

import com.healthsync.app.BuildConfig
import com.healthsync.app.network.request.BatchHealthDataRequest
import com.healthsync.app.network.request.LinkDeviceRequest
import com.healthsync.app.network.request.ValidateCodeRequest
import com.healthsync.app.network.response.BatchHealthDataResponse
import com.healthsync.app.network.response.LinkDeviceResponse
import com.healthsync.app.network.response.PatientInfoResponse
import com.healthsync.app.network.response.ValidateCodeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
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
        logging.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

        OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(logging)
            .addInterceptor(RetryInterceptor(maxRetries = 2))
            .build()
    }

    suspend fun <T> get(path: String, serializer: KSerializer<T>, token: String? = null): T = withContext(Dispatchers.IO) {
        val requestBuilder = Request.Builder()
            .url(BASE_URL + path)
        if (token != null) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }
        val request = requestBuilder.build()
        val response = client.newCall(request).execute()
        if (!response.isSuccessful) {
            val body = response.body?.string().orEmpty()
            throw ApiException(response.code, body)
        }
        val body = response.body?.string() ?: throw java.io.IOException("Empty body")
        json.decodeFromString(serializer, body)
    }

    suspend fun <T, R> post(path: String, body: T, bodySerializer: KSerializer<T>, responseSerializer: KSerializer<R>): R = withContext(Dispatchers.IO) {
        val jsonString = json.encodeToString(bodySerializer, body)
        val requestBody = jsonString.toRequestBody(mediaType)
        val request = Request.Builder()
            .url(BASE_URL + path)
            .post(requestBody)
            .build()
        val response = client.newCall(request).execute()
        if (!response.isSuccessful) {
            val respBody = response.body?.string().orEmpty()
            throw ApiException(response.code, respBody)
        }
        val responseBody = response.body?.string() ?: throw java.io.IOException("Empty body")
        json.decodeFromString(responseSerializer, responseBody)
    }
}
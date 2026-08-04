package com.healthsync.app.network

import kotlinx.coroutines.delay
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RetryInterceptor(
    private val maxRetries: Int = 2,
    private val baseDelayMillis: Long = 2000
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var retryCount = 0

        while (true) {
            try {
                val response = chain.proceed(request)

                if (response.code == 503 && retryCount < maxRetries) {
                    response.close()
                    retryCount++
                    val delayMillis = baseDelayMillis * retryCount
                    Thread.sleep(delayMillis)
                    request = request.newBuilder().build()
                    continue
                }

                return response
            } catch (e: IOException) {
                if (retryCount < maxRetries) {
                    retryCount++
                    val delayMillis = baseDelayMillis * retryCount
                    Thread.sleep(delayMillis)
                    request = request.newBuilder().build()
                    continue
                }
                throw e
            }
        }
    }
}

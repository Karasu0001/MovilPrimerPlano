package com.healthsync.app.network

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.delay

class RateLimiter(private val maxRequests: Int = 30, private val windowMillis: Long = 60_000) {

    private val requestTimestamps = mutableListOf<Long>()
    private val mutex = Mutex()

    suspend fun acquire() {
        mutex.withLock {
            val now = System.currentTimeMillis()
            requestTimestamps.removeAll { now - it > windowMillis }

            if (requestTimestamps.size >= maxRequests) {
                val oldest = requestTimestamps.first()
                val waitTime = windowMillis - (now - oldest) + 100L
                if (waitTime > 0) {
                    delay(waitTime)
                }
                val newNow = System.currentTimeMillis()
                requestTimestamps.removeAll { newNow - it > windowMillis }
            }
            requestTimestamps.add(System.currentTimeMillis())
        }
    }
}

package com.healthsync.app.network

class ApiException(val httpCode: Int, val rawBody: String) : Exception()
package com.healthsync.app.network

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val message: String, val statusCode: Int? = null) : NetworkResult<Nothing>()
    data object Loading : NetworkResult<Nothing>()
}

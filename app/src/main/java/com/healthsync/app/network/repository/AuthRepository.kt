package com.healthsync.app.network.repository

import com.healthsync.app.network.ApiException
import com.healthsync.app.network.ApiClient
import com.healthsync.app.network.NetworkResult
import com.healthsync.app.network.extractErrorMessage
import com.healthsync.app.network.request.LoginRequest
import com.healthsync.app.network.request.RegisterRequest
import com.healthsync.app.network.response.AuthResponse
import kotlinx.serialization.serializer
import kotlinx.coroutines.delay

class AuthRepository {

    suspend fun login(email: String, password: String): NetworkResult<AuthResponse> {
        return try {
            val response = ApiClient.post(
                "api/v1/auth/login",
                LoginRequest(email, password),
                LoginRequest.serializer(),
                AuthResponse.serializer()
            )
            NetworkResult.Success(response)
        } catch (e: ApiException) {
            when (e.httpCode) {
                401 -> {
                    val msg = extractErrorMessage(e.rawBody)
                    NetworkResult.Error(
                        msg ?: "Correo o contraseña incorrectos",
                        401
                    )
                }
                400 -> {
                    val msg = extractErrorMessage(e.rawBody)
                    NetworkResult.Error(
                        msg ?: "Error en los datos enviados",
                        400
                    )
                }
                else -> NetworkResult.Error(extractErrorMessage(e.rawBody) ?: "Error del servidor")
            }
        } catch (e: java.net.UnknownHostException) {
            NetworkResult.Error("Sin conexión a internet. Verificá tu red Wi-Fi o datos móviles.")
        } catch (e: java.net.SocketTimeoutException) {
            NetworkResult.Error("Conectando con el servidor, esto puede tardar unos segundos...")
        } catch (e: java.io.IOException) {
            NetworkResult.Error("Error de conexión. Reintentá en unos instantes.")
        } catch (e: Exception) {
            NetworkResult.Error("Error inesperado. Intentá nuevamente.")
        }
    }

    suspend fun register(
        name: String,
        lastname: String,
        phone: String,
        email: String,
        password: String
    ): NetworkResult<AuthResponse> {
        return try {
            val response = ApiClient.post(
                "api/v1/auth/register",
                RegisterRequest(name, lastname, phone, email = email, password = password),
                RegisterRequest.serializer(),
                AuthResponse.serializer()
            )
            NetworkResult.Success(response)
        } catch (e: ApiException) {
            when (e.httpCode) {
                400 -> {
                    val msg = extractErrorMessage(e.rawBody)
                    if (msg != null && msg.contains("Email", ignoreCase = true)) {
                        NetworkResult.Error("Ese correo ya está registrado", 400)
                    } else {
                        NetworkResult.Error(msg ?: "Error en los datos enviados", 400)
                    }
                }
                else -> NetworkResult.Error(extractErrorMessage(e.rawBody) ?: "Error del servidor")
            }
        } catch (e: java.net.UnknownHostException) {
            NetworkResult.Error("Sin conexión a internet. Verificá tu red Wi-Fi o datos móviles.")
        } catch (e: java.net.SocketTimeoutException) {
            NetworkResult.Error("Conectando con el servidor, esto puede tardar unos segundos...")
        } catch (e: java.io.IOException) {
            NetworkResult.Error("Error de conexión. Reintentá en unos instantes.")
        } catch (e: Exception) {
            NetworkResult.Error("Error inesperado. Intentá nuevamente.")
        }
    }

}
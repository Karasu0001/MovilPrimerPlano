package com.healthsync.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val rememberMe: Boolean = false,
    val showPassword: Boolean = false,
    val isSubmitting: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null
)

class LoginViewModel : ViewModel() {

    var uiState by mutableStateOf(LoginUiState())
        private set

    fun onEmailChanged(value: String) {
        uiState = uiState.copy(email = value, emailError = null)
    }

    fun onPasswordChanged(value: String) {
        uiState = uiState.copy(password = value, passwordError = null)
    }

    fun onRememberMeChanged(value: Boolean) {
        uiState = uiState.copy(rememberMe = value)
    }

    fun onTogglePasswordVisibility() {
        uiState = uiState.copy(showPassword = !uiState.showPassword)
    }

    fun login(onSuccess: () -> Unit) {
        var hasError = false
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

        if (uiState.email.isBlank()) {
            uiState = uiState.copy(emailError = "El correo electrónico es requerido")
            hasError = true
        } else if (!emailRegex.matches(uiState.email)) {
            uiState = uiState.copy(emailError = "Ingresa un correo electrónico válido")
            hasError = true
        }

        if (uiState.password.isBlank()) {
            uiState = uiState.copy(passwordError = "La contraseña es requerida")
            hasError = true
        } else if (uiState.password.length < 8) {
            uiState = uiState.copy(passwordError = "La contraseña debe tener al menos 8 caracteres")
            hasError = true
        }

        if (hasError) return

        uiState = uiState.copy(isSubmitting = true)
        // TODO: integrar con servicio de autenticación
        onSuccess()
        uiState = uiState.copy(isSubmitting = false)
    }
}

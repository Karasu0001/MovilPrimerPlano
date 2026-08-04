package com.healthsync.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class RegisterUiState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val showPassword: Boolean = false,
    val isSubmitting: Boolean = false,
    val fullNameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null
)

class RegisterViewModel : ViewModel() {

    var uiState by mutableStateOf(RegisterUiState())
        private set

    fun onFullNameChanged(value: String) {
        uiState = uiState.copy(fullName = value, fullNameError = null)
    }

    fun onEmailChanged(value: String) {
        uiState = uiState.copy(email = value, emailError = null)
    }

    fun onPasswordChanged(value: String) {
        uiState = uiState.copy(password = value, passwordError = null)
    }

    fun onConfirmPasswordChanged(value: String) {
        uiState = uiState.copy(confirmPassword = value, confirmPasswordError = null)
    }

    fun onTogglePasswordVisibility() {
        uiState = uiState.copy(showPassword = !uiState.showPassword)
    }

    fun register(onSuccess: () -> Unit) {
        var hasError = false
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

        if (uiState.fullName.isBlank()) {
            uiState = uiState.copy(fullNameError = "El nombre completo es requerido")
            hasError = true
        } else if (uiState.fullName.length < 3) {
            uiState = uiState.copy(fullNameError = "El nombre debe tener al menos 3 caracteres")
            hasError = true
        }

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

        if (uiState.confirmPassword.isBlank()) {
            uiState = uiState.copy(confirmPasswordError = "Debes confirmar la contraseña")
            hasError = true
        } else if (uiState.password != uiState.confirmPassword) {
            uiState = uiState.copy(confirmPasswordError = "Las contraseñas no coinciden")
            hasError = true
        }

        if (hasError) return

        uiState = uiState.copy(isSubmitting = true)
        // TODO: integrar con servicio de registro
        onSuccess()
        uiState = uiState.copy(isSubmitting = false)
    }
}

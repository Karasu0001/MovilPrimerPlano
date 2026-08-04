package com.healthsync.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class ForgotPasswordUiState(
    val email: String = "",
    val isSubmitting: Boolean = false,
    val emailError: String? = null,
    val isSubmitted: Boolean = false
)

class ForgotPasswordViewModel : ViewModel() {

    var uiState by mutableStateOf(ForgotPasswordUiState())
        private set

    fun onEmailChanged(value: String) {
        uiState = uiState.copy(email = value, emailError = null)
    }

    fun onSubmit() {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

        if (uiState.email.isBlank()) {
            uiState = uiState.copy(emailError = "El correo electrónico es requerido")
            return
        } else if (!emailRegex.matches(uiState.email)) {
            uiState = uiState.copy(emailError = "Ingresa un correo electrónico válido")
            return
        }

        uiState = uiState.copy(isSubmitting = true)
        // TODO: integrar con servicio de recuperación de contraseña
        uiState = uiState.copy(isSubmitting = false, isSubmitted = true)
    }

    fun onReset() {
        uiState = ForgotPasswordUiState()
    }
}

package com.healthsync.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthsync.app.data.AuthSessionStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ProfileUiState(
    val caregiverName: String? = null,
    val caregiverEmail: String? = null
)

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadSessionData()
    }

    private fun loadSessionData() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState(
                caregiverName = AuthSessionStore.getCaregiverName(),
                caregiverEmail = AuthSessionStore.getCaregiverEmail()
            )
        }
    }

    fun logout(onComplete: () -> Unit) {
        viewModelScope.launch {
            // Solo cierra la sesión de cuidador (JWT). PatientSessionStore es un
            // estado local de emparejamiento sin cuenta, no depende del cuidador logueado.
            AuthSessionStore.clearSession()
            onComplete()
        }
    }
}
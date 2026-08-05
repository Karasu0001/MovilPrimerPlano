package com.healthsync.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthsync.app.data.AuthSessionStore
import com.healthsync.app.network.NetworkResult
import com.healthsync.app.network.repository.DashboardRepository
import com.healthsync.app.network.response.CreatePatientRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CreatePatientUiState(
    val name: String = "",
    val age: String = "",
    val gender: String = "",
    val notes: String = "",
    val isLoading: Boolean = false,
    val networkError: String? = null,
    val success: Boolean = false
)

class CreatePatientViewModel : ViewModel() {

    private val dashboardRepository = DashboardRepository()

    private val _uiState = MutableStateFlow(CreatePatientUiState())
    val uiState: StateFlow<CreatePatientUiState> = _uiState.asStateFlow()

    fun onNameChange(value: String) {
        _uiState.value = _uiState.value.copy(name = value)
    }

    fun onAgeChange(value: String) {
        _uiState.value = _uiState.value.copy(age = value)
    }

    fun onGenderChange(value: String) {
        _uiState.value = _uiState.value.copy(gender = value)
    }

    fun onNotesChange(value: String) {
        _uiState.value = _uiState.value.copy(notes = value)
    }

    fun createPatient() {
        viewModelScope.launch {
            val token = AuthSessionStore.getToken()
            if (token == null) {
                _uiState.value = _uiState.value.copy(
                    networkError = "No se encontr\u00f3 la sesi\u00f3n."
                )
                return@launch
            }
            val age = _uiState.value.age.toIntOrNull()
            if (age == null || age <= 0) {
                _uiState.value = _uiState.value.copy(
                    networkError = "Ingres\u00e1 una edad v\u00e1lida"
                )
                return@launch
            }
            if (_uiState.value.name.isBlank()) {
                _uiState.value = _uiState.value.copy(
                    networkError = "Ingres\u00e1 el nombre del paciente"
                )
                return@launch
            }
            _uiState.value = _uiState.value.copy(isLoading = true, networkError = null)
            val request = CreatePatientRequest(
                name = _uiState.value.name,
                age = age,
                gender = _uiState.value.gender,
                notes = _uiState.value.notes
            )
            when (val result = dashboardRepository.createPatient(request, token)) {
                is NetworkResult.Success -> {
                    _uiState.value = CreatePatientUiState(success = true)
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        networkError = result.message
                    )
                }
                is NetworkResult.Loading -> Unit
            }
        }
    }

    fun clearNetworkError() {
        _uiState.value = _uiState.value.copy(networkError = null)
    }

    fun resetSuccess() {
        _uiState.value = _uiState.value.copy(success = false)
    }
}
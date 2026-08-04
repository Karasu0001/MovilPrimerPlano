package com.healthsync.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthsync.app.data.PatientSessionStore
import com.healthsync.app.network.NetworkResult
import com.healthsync.app.network.repository.HealthDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DashboardUiState(
    val patientId: String? = null,
    val patientName: String? = null,
    val heartRate: String? = null,
    val oxygenSaturation: String? = null,
    val activityLevel: String? = null,
    val lastSync: String? = null,
    val isLoading: Boolean = false,
    val networkError: String? = null
)

class DashboardViewModel : ViewModel() {

    private val healthDataRepository = HealthDataRepository()

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        observePatientSession()
    }

    private fun observePatientSession() {
        viewModelScope.launch {
            PatientSessionStore.observePatientId().collect { patientId ->
                _uiState.value = _uiState.value.copy(patientId = patientId)
                if (patientId != null) {
                    fetchPatientInfo(patientId)
                }
            }
        }
    }

    fun fetchPatientInfo(patientId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, networkError = null)
            when (val result = healthDataRepository.getPatientInfo(patientId)) {
                is NetworkResult.Success -> {
                    val data = result.data
                    _uiState.value = _uiState.value.copy(
                        patientName = data.patientName,
                        heartRate = data.heartRate?.toString(),
                        oxygenSaturation = data.oxygenSaturation?.toString(),
                        activityLevel = data.activityLevel?.toString(),
                        lastSync = data.lastSync,
                        isLoading = false
                    )
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
}
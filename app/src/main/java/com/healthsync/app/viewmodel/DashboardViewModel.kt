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
    val pairingCode: String? = null,
    val patientName: String? = null,
    val heartRate: String? = null,
    val oxygenSaturation: String? = null,
    val activityLevel: String? = null,
    val lastReadingAt: String? = null,
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
            PatientSessionStore.observePairingCode().collect { pairingCode ->
                _uiState.value = _uiState.value.copy(pairingCode = pairingCode)
                if (pairingCode != null) {
                    fetchPatientInfo(pairingCode)
                }
            }
        }
    }

    fun fetchPatientInfo(pairingCode: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, networkError = null)
            when (val result = healthDataRepository.getPatientInfo(pairingCode)) {
                is NetworkResult.Success -> {
                    val data = result.data
                    _uiState.value = _uiState.value.copy(
                        patientName = data.name,
                        heartRate = data.lastHeartRate?.toString(),
                        oxygenSaturation = data.lastOxygen?.toString(),
                        activityLevel = data.lastActivity?.toString(),
                        lastReadingAt = data.lastReadingAt,
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
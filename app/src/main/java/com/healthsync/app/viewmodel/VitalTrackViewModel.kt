package com.healthsync.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthsync.app.data.PatientSessionStore
import com.healthsync.app.network.NetworkResult
import com.healthsync.app.network.repository.HealthDataRepository
import com.healthsync.app.util.formatVital
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class VitalTrackUiState(
    val patientName: String? = null,
    val heartRate: String? = null,
    val oxygenSaturation: String? = null,
    val lastActivity: String? = null,
    val lastReadingAt: String? = null,
    val hasRealData: Boolean = false,
    val isLoading: Boolean = false,
    val networkError: String? = null
)

class VitalTrackViewModel : ViewModel() {

    private val healthDataRepository = HealthDataRepository()

    private val _uiState = MutableStateFlow(VitalTrackUiState())
    val uiState: StateFlow<VitalTrackUiState> = _uiState.asStateFlow()

    init {
        observePatientSession()
    }

    private fun observePatientSession() {
        viewModelScope.launch {
            PatientSessionStore.observePairingCode().collect { pairingCode ->
                if (pairingCode != null) {
                    val name = PatientSessionStore.getPatientName()
                    _uiState.value = _uiState.value.copy(patientName = name)
                    fetchPatientInfo(pairingCode)
                    while (true) {
                        delay(20_000)
                        fetchPatientInfo(pairingCode)
                    }
                } else {
                    _uiState.value = VitalTrackUiState(hasRealData = false)
                }
            }
        }
    }

    fun fetchPatientInfo(pairingCode: String) {
        viewModelScope.launch {
            val current = _uiState.value
            if (!current.hasRealData) {
                _uiState.value = current.copy(isLoading = true, networkError = null)
            }
            when (val result = healthDataRepository.getPatientInfo(pairingCode)) {
                is NetworkResult.Success -> {
                    val data = result.data
                    _uiState.value = VitalTrackUiState(
                        patientName = _uiState.value.patientName ?: PatientSessionStore.getPatientName(),
                        heartRate = data.lastHeartRate?.formatVital(),
                        oxygenSaturation = data.lastOxygen?.formatVital(),
                        lastActivity = data.lastActivity?.formatVital(),
                        lastReadingAt = data.lastReadingAt,
                        hasRealData = true,
                        isLoading = false
                    )
                }
                is NetworkResult.Error -> {
                    if (!_uiState.value.hasRealData) {
                        _uiState.value = _uiState.value.copy(
                            hasRealData = false,
                            isLoading = false,
                            networkError = result.message
                        )
                    }
                }
                is NetworkResult.Loading -> Unit
            }
        }
    }

    fun clearNetworkError() {
        _uiState.value = _uiState.value.copy(networkError = null)
    }
}

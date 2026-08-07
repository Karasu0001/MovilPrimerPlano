package com.healthsync.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthsync.app.data.PatientSessionStore
import com.healthsync.app.network.NetworkResult
import com.healthsync.app.network.repository.HealthDataRepository
import com.healthsync.app.util.formatVital
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class VitalTrackUiState(
    val heartRate: String? = null,
    val oxygenSaturation: String? = null,
    val heartRateRaw: Double? = null,
    val oxygenSaturationRaw: Double? = null,
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
                    fetchPatientInfo(pairingCode)
                } else {
                    _uiState.value = VitalTrackUiState(hasRealData = false)
                }
            }
        }
    }

    fun fetchPatientInfo(pairingCode: String) {
        viewModelScope.launch {
            _uiState.value = VitalTrackUiState(isLoading = true, networkError = null)
            when (val result = healthDataRepository.getPatientInfo(pairingCode)) {
                is NetworkResult.Success -> {
                    val data = result.data
                    _uiState.value = VitalTrackUiState(
                        heartRate = data.lastHeartRate?.formatVital(),
                        oxygenSaturation = data.lastOxygen?.formatVital(),
                        heartRateRaw = data.lastHeartRate,
                        oxygenSaturationRaw = data.lastOxygen,
                        hasRealData = true,
                        isLoading = false
                    )
                }
                is NetworkResult.Error -> {
                    _uiState.value = VitalTrackUiState(
                        hasRealData = false,
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
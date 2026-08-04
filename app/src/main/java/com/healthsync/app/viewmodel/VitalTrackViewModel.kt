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

data class VitalTrackUiState(
    val heartRate: String? = null,
    val oxygenSaturation: String? = null,
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
            PatientSessionStore.observePatientId().collect { patientId ->
                if (patientId != null) {
                    fetchPatientInfo(patientId)
                } else {
                    _uiState.value = VitalTrackUiState(hasRealData = false)
                }
            }
        }
    }

    fun fetchPatientInfo(patientId: String) {
        viewModelScope.launch {
            _uiState.value = VitalTrackUiState(isLoading = true, networkError = null)
            when (val result = healthDataRepository.getPatientInfo(patientId)) {
                is NetworkResult.Success -> {
                    val data = result.data
                    _uiState.value = VitalTrackUiState(
                        heartRate = data.heartRate?.toString(),
                        oxygenSaturation = data.oxygenSaturation?.toString(),
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
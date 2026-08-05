package com.healthsync.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthsync.app.data.AuthSessionStore
import com.healthsync.app.network.NetworkResult
import com.healthsync.app.network.repository.DashboardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PatientDetailUiState(
    val patientId: String = "",
    val patientName: String = "",
    val age: Int? = null,
    val gender: String? = null,
    val notes: String? = null,
    val hasDevice: Boolean = false,
    val lastHeartRate: Int? = null,
    val lastOxygen: Int? = null,
    val lastActivity: Int? = null,
    val lastReadingAt: String? = null,
    val generatedCode: String? = null,
    val codeExpiresIn: Int = 0,
    val isLoading: Boolean = false,
    val codeLoading: Boolean = false,
    val networkError: String? = null,
    val readings: List<ReadingDisplay> = emptyList()
)

class PatientDetailViewModel : ViewModel() {

    private val dashboardRepository = DashboardRepository()

    private val _uiState = MutableStateFlow(PatientDetailUiState())
    val uiState: StateFlow<PatientDetailUiState> = _uiState.asStateFlow()

    fun loadPatientDetail(patientId: String) {
        viewModelScope.launch {
            val token = AuthSessionStore.getToken()
            if (token == null) {
                _uiState.value = PatientDetailUiState(
                    networkError = "No se encontr\u00f3 la sesi\u00f3n. Inici\u00e1 sesi\u00f3n nuevamente."
                )
                return@launch
            }
            _uiState.value = PatientDetailUiState(patientId = patientId, isLoading = true, networkError = null)
            when (val result = dashboardRepository.getPatientDetail(patientId, token)) {
                is NetworkResult.Success -> {
                    val data = result.data
                    _uiState.value = PatientDetailUiState(
                        patientId = data.patientId,
                        patientName = data.name,
                        age = data.age,
                        gender = data.gender,
                        notes = data.notes,
                        hasDevice = data.hasDevice,
                        lastHeartRate = data.lastHeartRate,
                        lastOxygen = data.lastOxygen,
                        lastActivity = data.lastActivity,
                        lastReadingAt = data.lastReadingAt,
                        isLoading = false
                    )
                }
                is NetworkResult.Error -> {
                    _uiState.value = PatientDetailUiState(
                        patientId = patientId,
                        isLoading = false,
                        networkError = result.message
                    )
                }
                is NetworkResult.Loading -> Unit
            }
        }
    }

    fun generateCode(patientId: String) {
        viewModelScope.launch {
            val token = AuthSessionStore.getToken()
            if (token == null) return@launch
            _uiState.value = _uiState.value.copy(codeLoading = true)
            when (val result = dashboardRepository.generateWearableCode(patientId, token)) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        generatedCode = result.data.code,
                        codeExpiresIn = result.data.expiresInSeconds,
                        codeLoading = false
                    )
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        codeLoading = false,
                        networkError = result.message
                    )
                }
                is NetworkResult.Loading -> Unit
            }
        }
    }

    fun loadReadings(patientId: String) {
        viewModelScope.launch {
            val token = AuthSessionStore.getToken()
            if (token == null) return@launch
            when (val result = dashboardRepository.getReadings(patientId, token)) {
                is NetworkResult.Success -> {
                    val readings = result.data.readings
                        .filter { it.heartRate != null || it.oxygen != null }
                        .map {
                            ReadingDisplay(
                                timestamp = it.timestamp ?: "",
                                heartRate = it.heartRate ?: 0,
                                oxygen = it.oxygen ?: 0,
                                activity = it.activity ?: 0
                            )
                        }
                        .sortedByDescending { it.timestamp }
                    _uiState.value = _uiState.value.copy(readings = readings)
                }
                is NetworkResult.Error -> Unit
                is NetworkResult.Loading -> Unit
            }
        }
    }

    fun clearNetworkError() {
        _uiState.value = _uiState.value.copy(networkError = null)
    }
}
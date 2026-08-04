package com.healthsync.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthsync.app.data.AuthSessionStore
import com.healthsync.app.data.PatientSessionStore
import com.healthsync.app.network.NetworkResult
import com.healthsync.app.network.repository.DashboardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ActivityUiState(
    val readings: List<ReadingDisplay> = emptyList(),
    val isLoading: Boolean = false,
    val networkError: String? = null,
    val empty: Boolean = false
)

data class ReadingDisplay(
    val timestamp: String,
    val heartRate: Int,
    val oxygen: Int,
    val activity: Int
)

class ActivityViewModel : ViewModel() {

    private val dashboardRepository = DashboardRepository()

    private val _uiState = MutableStateFlow(ActivityUiState())
    val uiState: StateFlow<ActivityUiState> = _uiState.asStateFlow()

    init {
        loadReadings()
    }

    private fun loadReadings() {
        viewModelScope.launch {
            val patientId = PatientSessionStore.getPatientId()
            val token = AuthSessionStore.getToken()
            if (patientId == null || token == null) {
                _uiState.value = ActivityUiState(empty = true)
                return@launch
            }
            _uiState.value = ActivityUiState(isLoading = true, networkError = null)
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
                    _uiState.value = ActivityUiState(
                        readings = readings,
                        isLoading = false,
                        empty = readings.isEmpty()
                    )
                }
                is NetworkResult.Error -> {
                    _uiState.value = ActivityUiState(
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
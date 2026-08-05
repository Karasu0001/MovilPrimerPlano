package com.healthsync.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthsync.app.data.AuthSessionStore
import com.healthsync.app.network.NetworkResult
import com.healthsync.app.network.repository.DashboardRepository
import com.healthsync.app.network.response.PatientSummaryDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CaregiverDashboardUiState(
    val patients: List<PatientSummaryDto> = emptyList(),
    val totalPatients: Int = 0,
    val activeAlerts: Int = 0,
    val isLoading: Boolean = false,
    val networkError: String? = null
)

class CaregiverDashboardViewModel : ViewModel() {

    private val dashboardRepository = DashboardRepository()

    private val _uiState = MutableStateFlow(CaregiverDashboardUiState())
    val uiState: StateFlow<CaregiverDashboardUiState> = _uiState.asStateFlow()

    fun loadDashboard() {
        viewModelScope.launch {
            val token = AuthSessionStore.getToken()
            if (token == null) {
                _uiState.value = CaregiverDashboardUiState(
                    networkError = "No se encontr\u00f3 la sesi\u00f3n. Inici\u00e1 sesi\u00f3n nuevamente."
                )
                return@launch
            }
            _uiState.value = CaregiverDashboardUiState(isLoading = true, networkError = null)
            when (val result = dashboardRepository.getDashboard(token)) {
                is NetworkResult.Success -> {
                    val data = result.data
                    _uiState.value = CaregiverDashboardUiState(
                        patients = data.patients,
                        totalPatients = data.totalPatients,
                        activeAlerts = data.activeAlerts,
                        isLoading = false
                    )
                }
                is NetworkResult.Error -> {
                    _uiState.value = CaregiverDashboardUiState(
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
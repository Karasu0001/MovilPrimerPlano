package com.healthsync.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthsync.app.data.AuthSessionStore
import com.healthsync.app.network.NetworkResult
import com.healthsync.app.network.repository.DashboardRepository
import com.healthsync.app.network.response.AlertDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AlertsUiState(
    val alerts: List<AlertDto> = emptyList(),
    val isLoading: Boolean = false,
    val networkError: String? = null
)

class AlertsViewModel : ViewModel() {

    private val dashboardRepository = DashboardRepository()

    private val _uiState = MutableStateFlow(AlertsUiState())
    val uiState: StateFlow<AlertsUiState> = _uiState.asStateFlow()

    fun loadAlerts(patientId: String? = null) {
        viewModelScope.launch {
            val token = AuthSessionStore.getToken()
            if (token == null) {
                _uiState.value = AlertsUiState(
                    networkError = "No se encontr\u00f3 la sesi\u00f3n."
                )
                return@launch
            }
            _uiState.value = AlertsUiState(isLoading = true, networkError = null)
            val result = if (patientId != null) {
                dashboardRepository.getAlertsForPatient(patientId, token)
            } else {
                dashboardRepository.getAlerts(token)
            }
            when (result) {
                is NetworkResult.Success -> {
                    _uiState.value = AlertsUiState(
                        alerts = result.data.sortedByDescending { it.createdAt },
                        isLoading = false
                    )
                }
                is NetworkResult.Error -> {
                    _uiState.value = AlertsUiState(
                        isLoading = false,
                        networkError = result.message
                    )
                }
                is NetworkResult.Loading -> Unit
            }
        }
    }

    fun deleteAlert(alertId: String) {
        viewModelScope.launch {
            val token = AuthSessionStore.getToken()
            if (token == null) return@launch
            dashboardRepository.deleteAlert(alertId, token)
            loadAlerts()
        }
    }

    fun clearNetworkError() {
        _uiState.value = _uiState.value.copy(networkError = null)
    }
}
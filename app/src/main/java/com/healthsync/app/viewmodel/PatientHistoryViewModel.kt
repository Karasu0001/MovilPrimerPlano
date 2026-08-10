package com.healthsync.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthsync.app.data.PatientSessionStore
import com.healthsync.app.data.local.HealthSyncDb
import com.healthsync.app.data.local.ReadingEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PatientHistoryUiState(
    val readings: List<ReadingEntity> = emptyList(),
    val isLoading: Boolean = true
)

class PatientHistoryViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PatientHistoryUiState())
    val uiState: StateFlow<PatientHistoryUiState> = _uiState.asStateFlow()

    init {
        observeHistory()
    }

    private fun observeHistory() {
        viewModelScope.launch {
            val pairingCode = PatientSessionStore.getPairingCode()
            if (pairingCode.isNullOrEmpty()) {
                _uiState.value = PatientHistoryUiState(isLoading = false)
                return@launch
            }
            HealthSyncDb.readingDao().observeByPatient(pairingCode).collect { readings ->
                _uiState.value = PatientHistoryUiState(readings = readings, isLoading = false)
            }
        }
    }
}

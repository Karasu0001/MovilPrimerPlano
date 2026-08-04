package com.healthsync.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthsync.app.data.AuthSessionStore
import com.healthsync.app.data.PatientSessionStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ProfileUiState(
    val caregiverName: String? = null,
    val caregiverEmail: String? = null,
    val patientName: String? = null,
    val deviceSerialNumber: String? = null,
    val pairingCode: String? = null,
    val hasPatientLinked: Boolean = false
)

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadSessionData()
    }

    private fun loadSessionData() {
        viewModelScope.launch {
            val caregiverName = AuthSessionStore.getCaregiverName()
            val caregiverEmail = AuthSessionStore.getCaregiverEmail()
            val patientName = PatientSessionStore.getPatientName()
            val deviceSerialNumber = PatientSessionStore.getPairingCode()?.let { "Dispositivo vinculado" }
            val pairingCode = PatientSessionStore.getPairingCode()

            _uiState.value = ProfileUiState(
                caregiverName = caregiverName,
                caregiverEmail = caregiverEmail,
                patientName = patientName,
                deviceSerialNumber = deviceSerialNumber,
                pairingCode = pairingCode,
                hasPatientLinked = pairingCode != null
            )
        }
    }

    fun logout(onComplete: () -> Unit) {
        viewModelScope.launch {
            AuthSessionStore.clearSession()
            PatientSessionStore.clearPatientSession()
            onComplete()
        }
    }
}
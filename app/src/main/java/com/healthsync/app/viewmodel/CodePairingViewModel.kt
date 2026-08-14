package com.healthsync.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthsync.app.data.DeviceIdentifier
import com.healthsync.app.data.PatientSessionStore
import com.healthsync.app.network.NetworkResult
import com.healthsync.app.network.repository.PairingRepository
import com.healthsync.app.network.response.LinkDeviceResponse
import com.healthsync.app.network.response.ValidateCodeResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class PairingStep {
    data object Searching : PairingStep()
    data object Confirming : PairingStep()
    data object Syncing : PairingStep()
    data class SyncingProgress(val progress: Int) : PairingStep()
    data object Success : PairingStep()
    data object Cancelled : PairingStep()
}

data class CodePairingUiState(
    val step: PairingStep = PairingStep.Searching,
    val pairingCode: String = "",
    val deviceName: String = "Dispositivo",
    val isLoading: Boolean = false,
    val networkError: String? = null,
    val patientCode: String? = null
)

class CodePairingViewModel : ViewModel() {

    private val pairingRepository = PairingRepository()

    var uiState by mutableStateOf(CodePairingUiState())
        private set

    fun onPairingCodeChanged(value: String) {
        val digitsOnly = value.filter { it.isDigit() }.take(6)
        uiState = uiState.copy(pairingCode = digitsOnly, networkError = null)
    }

    fun onConnect() {
        uiState = uiState.copy(step = PairingStep.Confirming)
    }

    fun onConfirmFromPhone() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, networkError = null)
            val code = uiState.pairingCode.replace(" ", "")
            val validateResult = pairingRepository.validateCode(code)
            handleValidateResult(validateResult)
        }
    }

    fun onAcceptFromWatch() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, networkError = null)
            val code = uiState.pairingCode.replace(" ", "")
            val validateResult = pairingRepository.validateCode(code)
            handleValidateResult(validateResult)
        }
    }

    private suspend fun handleValidateResult(validateResult: NetworkResult<ValidateCodeResponse>) {
        when (validateResult) {
            is NetworkResult.Success -> {
                val patientId = validateResult.data.patientId ?: ""
                val serialNumber = DeviceIdentifier.getId()
                val linkResult = pairingRepository.linkDevice(
                    uiState.pairingCode.replace(" ", ""),
                    serialNumber
                )
                when (linkResult) {
                    is NetworkResult.Success -> {
                        viewModelScope.launch {
                            PatientSessionStore.savePatientSession(
                                patientId = patientId,
                                pairingCode = uiState.pairingCode.replace(" ", ""),
                                patientName = validateResult.data.patientName
                            )
                        }
                        uiState = uiState.copy(
                            step = PairingStep.Syncing,
                            isLoading = false,
                            patientCode = patientId
                        )
                        startSyncProgress()
                    }
                    is NetworkResult.Error -> {
                        uiState = uiState.copy(
                            isLoading = false,
                            networkError = linkResult.message
                        )
                    }
                    is NetworkResult.Loading -> Unit
                }
            }
            is NetworkResult.Error -> {
                uiState = uiState.copy(
                    isLoading = false,
                    networkError = validateResult.message
                )
            }
            is NetworkResult.Loading -> Unit
        }
    }

    fun onRejectFromWatch() {
        uiState = uiState.copy(step = PairingStep.Cancelled)
    }

    fun onCancel() {
        uiState = uiState.copy(step = PairingStep.Cancelled)
    }

    fun onRetry() {
        // Vuelve a la pantalla de ingresar código (no reenvía el mismo código
        // que acaba de fallar): si el motivo del rechazo fue que ese código ya
        // se usó, reintentar con el mismo código solo iba a fallar de nuevo en
        // loop, sin darle al usuario forma de escribir uno distinto.
        uiState = uiState.copy(
            step = PairingStep.Searching,
            pairingCode = "",
            networkError = null
        )
    }

    fun onSyncComplete() {
        uiState = uiState.copy(step = PairingStep.Success)
    }

    fun onGoHome() {
        uiState = uiState.copy(
            step = PairingStep.Searching,
            pairingCode = "",
            networkError = null
        )
    }

    fun clearNetworkError() {
        uiState = uiState.copy(networkError = null)
    }

    private fun startSyncProgress() {
        viewModelScope.launch {
            for (i in 0..100) {
                uiState = uiState.copy(step = PairingStep.SyncingProgress(i))
                delay(35)
            }
            delay(400)
            uiState = uiState.copy(step = PairingStep.Success)
        }
    }
}
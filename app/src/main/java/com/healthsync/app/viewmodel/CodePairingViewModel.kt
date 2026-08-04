package com.healthsync.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.healthsync.app.network.NetworkResult
import com.healthsync.app.network.repository.PairingRepository
import com.healthsync.app.network.repository.ValidateCodeResult
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
    val pairingCode: String = "458 921",
    val deviceName: String = "Dialitech Watch Pro",
    val deviceModel: String = "DL-W3",
    val signalStrength: String = "Señal Excelente",
    val batteryLevel: String = "85%",
    val isLoading: Boolean = false,
    val networkError: String? = null,
    val patientCode: String? = null
)

class CodePairingViewModel : ViewModel() {

    private val pairingRepository = PairingRepository()

    var uiState by mutableStateOf(CodePairingUiState())
        private set

    fun onConnect() {
        uiState = uiState.copy(step = PairingStep.Confirming)
    }

    fun onConfirmFromPhone() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, networkError = null)
            val validateResult = pairingRepository.validateCode(uiState.pairingCode)
            handleValidateResult(validateResult)
        }
    }

    fun onAcceptFromWatch() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, networkError = null)
            val validateResult = pairingRepository.validateCode(uiState.pairingCode)
            handleValidateResult(validateResult)
        }
    }

    private suspend fun handleValidateResult(validateResult: NetworkResult<ValidateCodeResult>) {
        when (validateResult) {
            is NetworkResult.Success -> {
                val patientCode = validateResult.data.patientCode ?: ""
                val linkResult = pairingRepository.linkDevice(
                    uiState.pairingCode,
                    uiState.deviceModel
                )
                when (linkResult) {
                    is NetworkResult.Success -> {
                        uiState = uiState.copy(
                            step = PairingStep.Syncing,
                            isLoading = false,
                            patientCode = patientCode
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
        uiState = uiState.copy(
            step = PairingStep.Confirming,
            networkError = null
        )
    }

    fun onSyncComplete() {
        uiState = uiState.copy(step = PairingStep.Success)
    }

    fun onGoHome() {
        uiState = uiState.copy(
            step = PairingStep.Searching,
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
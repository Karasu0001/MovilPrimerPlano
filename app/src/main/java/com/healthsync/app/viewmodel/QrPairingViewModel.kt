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
import com.healthsync.app.network.response.ValidateCodeResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class QrPairingStep {
    data object CameraRequest : QrPairingStep()
    data object Scanning : QrPairingStep()
    data object Confirming : QrPairingStep()
    data object Syncing : QrPairingStep()
    data class SyncingProgress(val progress: Int) : QrPairingStep()
    data object Success : QrPairingStep()
    data object Cancelled : QrPairingStep()
}

data class QrPairingUiState(
    val step: QrPairingStep = QrPairingStep.CameraRequest,
    val qrCodeData: String = "",
    val deviceName: String = "Dispositivo",
    val scannedCode: String? = null,
    val isLoading: Boolean = false,
    val networkError: String? = null
)

class QrPairingViewModel : ViewModel() {

    private val pairingRepository = PairingRepository()

    var uiState by mutableStateOf(QrPairingUiState())
        private set

    fun onCameraPermissionGranted() {
        uiState = uiState.copy(step = QrPairingStep.Scanning)
    }

    fun onQrScanned(scannedData: String) {
        uiState = uiState.copy(
            scannedCode = scannedData,
            step = QrPairingStep.Confirming
        )
    }

    fun onConfirm() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, networkError = null)
            val code = extractPairingCode(uiState.scannedCode ?: uiState.qrCodeData)
            val serialNumber = DeviceIdentifier.getId()
            val validateResult = pairingRepository.validateCode(code)
            handleValidateResult(validateResult, code, serialNumber)
        }
    }

    private suspend fun handleValidateResult(
        validateResult: NetworkResult<ValidateCodeResponse>,
        code: String,
        serialNumber: String
    ) {
        when (validateResult) {
            is NetworkResult.Success -> {
                val patientId = validateResult.data.patientId ?: ""
                val linkResult = pairingRepository.linkDevice(code, serialNumber)
                when (linkResult) {
                    is NetworkResult.Success -> {
                        viewModelScope.launch {
                            PatientSessionStore.savePatientSession(
                                patientId = patientId,
                                pairingCode = code,
                                patientName = validateResult.data.patientName
                            )
                        }
                        uiState = uiState.copy(
                            step = QrPairingStep.Syncing,
                            isLoading = false
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

    fun onCancel() {
        uiState = uiState.copy(step = QrPairingStep.Cancelled)
    }

    fun onRetry() {
        uiState = uiState.copy(
            step = QrPairingStep.Scanning,
            scannedCode = null,
            isLoading = false,
            networkError = null
        )
    }

    fun onGoHome() {
        uiState = uiState.copy(
            step = QrPairingStep.CameraRequest,
            scannedCode = null,
            isLoading = false,
            networkError = null
        )
    }

    fun onSyncComplete() {
        uiState = uiState.copy(step = QrPairingStep.Success)
    }

    private fun extractPairingCode(rawQrData: String): String {
        return rawQrData.substringAfterLast(":")
    }

    private fun startSyncProgress() {
        viewModelScope.launch {
            for (i in 0..100) {
                uiState = uiState.copy(step = QrPairingStep.SyncingProgress(i))
                delay(35)
            }
            delay(400)
            uiState = uiState.copy(step = QrPairingStep.Success)
        }
    }
}
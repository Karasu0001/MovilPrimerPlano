package com.healthsync.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val qrCodeData: String = "dialitech:pair:9f8e7d6c5b4a3",
    val deviceName: String = "Dialitech Watch Pro",
    val deviceModel: String = "DL-W3",
    val scannedCode: String? = null
)

class QrPairingViewModel : ViewModel() {

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

    fun onSimulateScan() {
        uiState = uiState.copy(
            scannedCode = uiState.qrCodeData,
            step = QrPairingStep.Confirming
        )
    }

    fun onConfirm() {
        uiState = uiState.copy(step = QrPairingStep.Syncing)
        startSyncProgress()
    }

    fun onCancel() {
        uiState = uiState.copy(step = QrPairingStep.Cancelled)
    }

    fun onRetry() {
        uiState = uiState.copy(
            step = QrPairingStep.Scanning,
            scannedCode = null
        )
    }

    fun onGoHome() {
        uiState = uiState.copy(
            step = QrPairingStep.CameraRequest,
            scannedCode = null
        )
    }

    fun onSyncComplete() {
        uiState = uiState.copy(step = QrPairingStep.Success)
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

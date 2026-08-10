package com.healthsync.app.ui.screens.pairing.qr

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.healthsync.app.ui.theme.ColorBgPage
import com.healthsync.app.ui.theme.ColorTextPrimary
import com.healthsync.app.viewmodel.QrPairingStep
import com.healthsync.app.viewmodel.QrPairingViewModel

@Composable
fun QrPairingFlowScreen(
    onGoToVitalTrack: () -> Unit,
    onBack: () -> Unit = {},
    viewModel: QrPairingViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBgPage)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Vinculación por QR",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = ColorTextPrimary
            )
        }

        HorizontalDivider()

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when (val step = state.step) {
                is QrPairingStep.CameraRequest -> {
                    CameraPermissionScreen(
                        onPermissionGranted = { viewModel.onCameraPermissionGranted() },
                        onBack = onBack
                    )
                }
                is QrPairingStep.Scanning -> {
                    QrScanScreen(
                        onQrScanned = { viewModel.onQrScanned(it) },
                        onBack = onBack
                    )
                }
                is QrPairingStep.Confirming -> {
                    QrConfirmScreen(
                        onConfirm = { viewModel.onConfirm() },
                        onCancel = { viewModel.onCancel() },
                        deviceName = state.deviceName,
                        scannedCode = state.scannedCode ?: state.qrCodeData,
                        isLoading = state.isLoading,
                        errorMessage = state.networkError
                    )
                }
                is QrPairingStep.Syncing,
                is QrPairingStep.SyncingProgress -> {
                    QrSyncScreen(
                        onComplete = { viewModel.onSyncComplete() },
                        onCancel = { viewModel.onCancel() }
                    )
                }
                is QrPairingStep.Success -> {
                    QrSuccessScreen(
                        onGoToVitalTrack = onGoToVitalTrack,
                        onConfigureDevice = { }
                    )
                }
                is QrPairingStep.Cancelled -> {
                    QrCancelledScreen(
                        onRetry = { viewModel.onRetry() },
                        onGoHome = { viewModel.onGoHome() }
                    )
                }
            }
        }
    }
}

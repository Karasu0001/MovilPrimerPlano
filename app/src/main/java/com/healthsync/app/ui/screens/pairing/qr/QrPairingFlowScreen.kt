package com.healthsync.app.ui.screens.pairing.qr

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.healthsync.app.ui.components.WearableFrame
import com.healthsync.app.ui.components.WearableTheme
import com.healthsync.app.ui.theme.ColorBgPage
import com.healthsync.app.ui.theme.ColorTextPrimary
import com.healthsync.app.ui.theme.ColorTextSecondary
import com.healthsync.app.viewmodel.QrPairingStep
import com.healthsync.app.viewmodel.QrPairingViewModel

@Composable
fun QrPairingFlowScreen(
    onGoToDashboard: () -> Unit,
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

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier.weight(1f)) {
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
                            onSimulateScan = { viewModel.onSimulateScan() },
                            onBack = onBack
                        )
                    }
                    is QrPairingStep.Confirming -> {
                        QrConfirmScreen(
                            onConfirm = { viewModel.onConfirm() },
                            onCancel = { viewModel.onCancel() },
                            deviceName = state.deviceName,
                            scannedCode = state.scannedCode ?: state.qrCodeData
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
                            onGoToDashboard = onGoToDashboard,
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

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxSize()
                    .background(Color(0xFFE5E7EB))
            )

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Smartwatch — Dialitech Watch Pro",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ColorTextSecondary
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    when (val step = state.step) {
                        is QrPairingStep.CameraRequest -> {
                            WearableFrame(theme = WearableTheme.Dark) {
                                WearableShowQrContent(qrData = state.qrCodeData)
                            }
                        }
                        is QrPairingStep.Scanning -> {
                            WearableFrame(theme = WearableTheme.Dark) {
                                WearableShowQrContent(qrData = state.qrCodeData)
                            }
                        }
                        is QrPairingStep.Confirming -> {
                            WearableFrame(theme = WearableTheme.Dark) {
                                WearableQrConfirmContent(
                                    onAccept = { viewModel.onConfirm() },
                                    onReject = { viewModel.onCancel() }
                                )
                            }
                        }
                        is QrPairingStep.Syncing,
                        is QrPairingStep.SyncingProgress -> {
                            WearableFrame(theme = WearableTheme.Dark) {
                                WearableQrSyncingContent()
                            }
                        }
                        is QrPairingStep.Success -> {
                            WearableFrame(theme = WearableTheme.Light) {
                                WearableQrSuccessContent(onDismiss = onGoToDashboard)
                            }
                        }
                        is QrPairingStep.Cancelled -> {
                            WearableFrame(theme = WearableTheme.Dark) {
                                WearableQrCancelledContent(onClose = { viewModel.onGoHome() })
                            }
                        }
                    }
                }
            }
        }
    }
}

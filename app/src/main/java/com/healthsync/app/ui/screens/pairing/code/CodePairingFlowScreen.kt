package com.healthsync.app.ui.screens.pairing.code

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
import com.healthsync.app.viewmodel.CodePairingViewModel
import com.healthsync.app.viewmodel.PairingStep

@Composable
fun CodePairingFlowScreen(
    onGoToDashboard: () -> Unit,
    onBack: () -> Unit = {},
    viewModel: CodePairingViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBgPage)
    ) {
        // Toggle buttons para simular interacción del wearable
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Vinculación por Código",
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
            // Panel izquierdo: Teléfono
            Box(modifier = Modifier.weight(1f)) {
                when (val step = state.step) {
                    is PairingStep.Searching -> {
                        BuscandoDispositivoScreen(onConnect = { viewModel.onConnect() })
                    }
                    is PairingStep.Confirming -> {
                        ConfirmarEmparejamientoScreen(
                            onConfirm = { viewModel.onConfirmFromPhone() },
                            onCancel = { viewModel.onCancel() },
                            pairingCode = state.pairingCode,
                            isLoading = state.isLoading,
                            errorMessage = state.networkError
                        )
                    }
                    is PairingStep.Syncing,
                    is PairingStep.SyncingProgress -> {
                        SincronizandoDispositivoScreen(
                            onComplete = { viewModel.onSyncComplete() },
                            onCancel = { viewModel.onCancel() }
                        )
                    }
                    is PairingStep.Success -> {
                        VinculacionExitosaScreen(
                            onGoToDashboard = onGoToDashboard,
                            onConfigureDevice = { /* TODO */ }
                        )
                    }
                    is PairingStep.Cancelled -> {
                        VinculacionCanceladaScreen(
                            onRetry = { viewModel.onRetry() },
                            onGoHome = { viewModel.onGoHome() },
                            errorMessage = state.networkError
                        )
                    }
                }
            }

            // Divider vertical
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxSize()
                    .background(Color(0xFFE5E7EB))
            )

            // Panel derecho: Wearable simulado
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
                        is PairingStep.Confirming -> {
                            WearableFrame(
                                theme = WearableTheme.Dark
                            ) {
                                WearableConfirmarCodigoContent(
                                    pairingCode = state.pairingCode,
                                    onAccept = { viewModel.onAcceptFromWatch() },
                                    onReject = { viewModel.onRejectFromWatch() }
                                )
                            }
                        }
                        is PairingStep.Syncing,
                        is PairingStep.SyncingProgress -> {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                WearableFrame(theme = WearableTheme.Dark) {
                                    WearableVinculandoContent()
                                }
                                WearableFrame(theme = WearableTheme.Dark) {
                                    WearableSincronizarAhoraContent()
                                }
                            }
                        }
                        is PairingStep.Success -> {
                            WearableFrame(theme = WearableTheme.Light) {
                                WearableDispositivoVinculadoContent(
                                    onDismiss = onGoToDashboard
                                )
                            }
                        }
                        is PairingStep.Cancelled -> {
                            WearableFrame(theme = WearableTheme.Dark) {
                                WearableSolicitudRechazadaContent(
                                    onClose = { viewModel.onGoHome() },
                                    errorMessage = state.networkError
                                )
                            }
                        }
                        is PairingStep.Searching -> {
                            Box(
                                modifier = Modifier
                                    .size(280.dp)
                                    .clip(RoundedCornerShape(40.dp))
                                    .background(Color(0xFF0B0F1A)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Esperando conexión...",
                                    color = Color(0xFF94A3B8),
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

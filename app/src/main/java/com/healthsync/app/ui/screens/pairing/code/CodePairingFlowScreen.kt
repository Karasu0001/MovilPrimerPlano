package com.healthsync.app.ui.screens.pairing.code

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

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when (val step = state.step) {
                is PairingStep.Searching -> {
                    IngresarCodigoScreen(
                        pairingCode = state.pairingCode,
                        onPairingCodeChanged = { viewModel.onPairingCodeChanged(it) },
                        onContinue = { viewModel.onConnect() }
                    )
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
    }
}

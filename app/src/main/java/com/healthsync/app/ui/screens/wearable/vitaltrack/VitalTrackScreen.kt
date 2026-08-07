package com.healthsync.app.ui.screens.wearable.vitaltrack


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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.healthsync.app.ui.components.AppCard
import com.healthsync.app.ui.components.WearableFrame
import com.healthsync.app.ui.components.WearableTheme
import com.healthsync.app.ui.theme.ColorBgPage
import com.healthsync.app.ui.theme.ColorPrimary
import com.healthsync.app.ui.theme.ColorSuccessGreen
import com.healthsync.app.ui.theme.ColorTextMuted
import com.healthsync.app.ui.theme.ColorTextPrimary
import com.healthsync.app.ui.theme.ColorTextSecondary
import com.healthsync.app.ui.theme.ColorVtBg
import com.healthsync.app.ui.theme.ColorVtRingSuccess
import com.healthsync.app.ui.theme.ColorVtRingWarning
import com.healthsync.app.ui.theme.ColorWearableRingAccentBlue
import com.healthsync.app.ui.theme.ColorWearableRingAccentTeal
import com.healthsync.app.ui.theme.ColorWearableText
import com.healthsync.app.ui.theme.ColorWearableTextMuted
import com.healthsync.app.viewmodel.VitalTrackViewModel

@Composable
fun VitalTrackScreen(
    onBack: () -> Unit = {}
) {
    val vitalTrackViewModel: VitalTrackViewModel = viewModel()
    val uiState by vitalTrackViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        vitalTrackViewModel.clearNetworkError()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorVtBg)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(ColorVtBg)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "<",
                fontSize = 18.sp,
                color = ColorWearableTextMuted,
                modifier = Modifier.padding(end = 12.dp)
            )
            Text(
                text = "VitalTrack",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = ColorWearableText
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WearableFrame(
                theme = WearableTheme.Dark,
                label = "MONITOREO EN VIVO",
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "\u2665",
                        fontSize = 48.sp,
                        color = ColorWearableRingAccentTeal
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = uiState.heartRate ?: "--",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = ColorWearableText
                    )
                    Text(
                        text = "lpm",
                        fontSize = 14.sp,
                        color = ColorWearableTextMuted
                    )
                    if (uiState.oxygenSaturation != null) {
                        val o2 = uiState.oxygenSaturation
                        Spacer(modifier = Modifier.height(16.dp))
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("O2", fontSize = 10.sp, color = ColorWearableTextMuted)
                            Text(
                                text = o2 ?: "",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = ColorWearableText
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            AppCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Signos Vitales",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = ColorTextPrimary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    VitalSignRow(
                        "Frecuencia card\u00edaca",
                        uiState.heartRate?.let { "$it lpm" } ?: "--",
                        if (uiState.heartRate != null) "Normal" else "Sin datos",
                        if (uiState.heartRate != null) ColorSuccessGreen else ColorVtRingWarning
                    )
                    VitalSignRow(
                        "Saturaci\u00f3n O2",
                        uiState.oxygenSaturation?.let { "$it%" } ?: "--",
                        if (uiState.oxygenSaturation != null) "Normal" else "Sin datos",
                        if (uiState.oxygenSaturation != null) ColorSuccessGreen else ColorVtRingWarning
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            val networkError = uiState.networkError
            if (networkError != null) {
                Text(
                    text = networkError,
                    fontSize = 12.sp,
                    color = ColorTextMuted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            } else if (uiState.hasRealData) {
                Text(
                    text = "Datos obtenidos del dispositivo vinculado.",
                    fontSize = 12.sp,
                    color = ColorTextMuted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            } else {
                Text(
                    text = "Todav\u00eda no hay lecturas de este paciente.",
                    fontSize = 12.sp,
                    color = ColorTextMuted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun VitalSignRow(label: String, value: String, status: String, statusColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = ColorTextSecondary,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = ColorTextPrimary,
            modifier = Modifier.padding(end = 12.dp)
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(9999.dp))
                .background(statusColor.copy(alpha = 0.1f))
                .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            Text(
                text = status,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = statusColor
            )
        }
    }
}
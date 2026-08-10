package com.healthsync.app.ui.screens.wearable.vitaltrack

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.healthsync.app.ui.components.StatCard
import com.healthsync.app.ui.theme.ColorBgPage
import com.healthsync.app.ui.theme.ColorErrorRed
import com.healthsync.app.ui.theme.ColorPrimary
import com.healthsync.app.ui.theme.ColorSuccessGreen
import com.healthsync.app.ui.theme.ColorTextMuted
import com.healthsync.app.ui.theme.ColorTextPrimary
import com.healthsync.app.ui.theme.ColorTextSecondary
import com.healthsync.app.viewmodel.VitalTrackViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun VitalTrackScreen(
    onBack: () -> Unit = {},
    onViewHistory: () -> Unit = {}
) {
    val vitalTrackViewModel: VitalTrackViewModel = viewModel()
    val uiState by vitalTrackViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        vitalTrackViewModel.clearNetworkError()
    }

    val today = SimpleDateFormat("EEEE d 'de' MMMM", Locale("es")).format(Date())
        .replaceFirstChar { it.uppercase() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBgPage)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "<",
                fontSize = 18.sp,
                color = ColorTextMuted,
                modifier = Modifier
                    .clickable { onBack() }
                    .padding(end = 12.dp)
            )
            Text(
                text = "VitalTrack",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = ColorTextPrimary
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Hola, ${uiState.patientName ?: ""}".trimEnd(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = ColorPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = today,
                fontSize = 14.sp,
                color = ColorTextSecondary
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (uiState.isLoading && !uiState.hasRealData) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(32.dp))
                    CircularProgressIndicator(color = ColorPrimary)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Cargando datos...",
                        fontSize = 14.sp,
                        color = ColorTextMuted
                    )
                }
            } else {
                val networkError = uiState.networkError
                if (networkError != null && !uiState.hasRealData) {
                    Text(
                        text = networkError,
                        fontSize = 13.sp,
                        color = ColorErrorRed,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatCard(
                            icon = Icons.Default.Favorite,
                            value = uiState.heartRate ?: "--",
                            label = "Frec. card\u00edaca",
                            accentColor = ColorErrorRed,
                            modifier = Modifier.weight(1f)
                        )
                        StatCard(
                            icon = Icons.Default.WaterDrop,
                            value = uiState.oxygenSaturation?.let { "$it%" } ?: "--",
                            label = "Saturaci\u00f3n O2",
                            accentColor = ColorPrimary,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatCard(
                            icon = Icons.AutoMirrored.Filled.DirectionsRun,
                            value = uiState.lastActivity ?: "--",
                            label = "Actividad",
                            accentColor = ColorSuccessGreen,
                            modifier = Modifier.weight(1f)
                        )
                        StatCard(
                            icon = Icons.Default.AccessTime,
                            value = uiState.lastReadingAt?.let { formatReadingTime(it) } ?: "--",
                            label = "\u00daltima lectura",
                            accentColor = ColorTextSecondary,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Ver historial de lecturas →",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ColorPrimary,
                    modifier = Modifier.clickable { onViewHistory() }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

private fun formatReadingTime(iso: String): String {
    return try {
        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).apply {
            timeZone = java.util.TimeZone.getTimeZone("UTC")
        }
        // El output usa la zona horaria local del dispositivo (default de SimpleDateFormat),
        // así que esto convierte de UTC (lo que manda la API) a la hora local del paciente.
        val output = SimpleDateFormat("HH:mm", Locale.US)
        val date = input.parse(iso) ?: return iso
        output.format(date)
    } catch (_: Exception) {
        iso.take(16)
    }
}

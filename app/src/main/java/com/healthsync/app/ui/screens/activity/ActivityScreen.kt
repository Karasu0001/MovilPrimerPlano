package com.healthsync.app.ui.screens.activity

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.healthsync.app.navigation.Routes
import com.healthsync.app.ui.components.AppCard
import com.healthsync.app.ui.components.BottomNavBar
import com.healthsync.app.ui.theme.ColorBgPage
import com.healthsync.app.ui.theme.ColorCodeBoxBg
import com.healthsync.app.ui.theme.ColorCodeText
import com.healthsync.app.ui.theme.ColorPrimary
import com.healthsync.app.ui.theme.ColorTextMuted
import com.healthsync.app.ui.theme.ColorTextPrimary
import com.healthsync.app.ui.theme.ColorTextSecondary
import com.healthsync.app.viewmodel.ActivityViewModel
import com.healthsync.app.util.formatVital

@Composable
fun ActivityScreen(
    onBack: () -> Unit = {},
    onNavigateToDashboard: () -> Unit = {},
    onNavigateToPairing: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    val activityViewModel: ActivityViewModel = viewModel()
    val uiState by activityViewModel.uiState.collectAsState()

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
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Atras",
                    tint = ColorTextPrimary
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Actividad",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = ColorTextPrimary
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val networkError = uiState.networkError
            if (uiState.isLoading) {
                Text(
                    text = "Cargando lecturas...",
                    fontSize = 14.sp,
                    color = ColorTextMuted
                )
            } else if (networkError != null) {
                Text(
                    text = networkError,
                    fontSize = 14.sp,
                    color = ColorTextMuted,
                    textAlign = TextAlign.Center
                )
            } else if (uiState.empty) {
                Text(
                    text = "No hay lecturas disponibles. Vincula un dispositivo para comenzar a monitorear.",
                    fontSize = 14.sp,
                    color = ColorTextMuted,
                    textAlign = TextAlign.Center
                )
            } else {
                uiState.readings.forEach { reading ->
                    AppCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = reading.timestamp,
                                fontSize = 11.sp,
                                color = ColorTextMuted
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            ReadingRow("Frecuencia cardíaca", "${reading.heartRate.formatVital()} lpm")
                            ReadingRow("Saturación O2", "${reading.oxygen.formatVital()}%")
                            ReadingRow("Actividad", reading.activity.formatVital())
                        }
                    }
                }
            }
        }

        BottomNavBar(
            activeRoute = Routes.Activity.route,
            onItemClick = { route ->
                when (route) {
                    Routes.Dashboard.route -> onNavigateToDashboard()
                    Routes.PairingMethod.route -> onNavigateToPairing()
                    Routes.Activity.route -> Unit
                    Routes.Profile.route -> onNavigateToProfile()
                    else -> Unit
                }
            }
        )
    }
}

@Composable
private fun ReadingRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
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
            color = ColorTextPrimary
        )
    }
}
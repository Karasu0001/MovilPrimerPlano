package com.healthsync.app.ui.screens.dashboard

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.healthsync.app.navigation.Routes
import com.healthsync.app.ui.components.AppCard
import com.healthsync.app.ui.components.BottomNavBar
import com.healthsync.app.ui.components.BrandLogo
import com.healthsync.app.ui.components.BrandLogoSize
import com.healthsync.app.ui.theme.ColorBgPage
import com.healthsync.app.ui.theme.ColorInputBg
import com.healthsync.app.ui.theme.ColorPrimary
import com.healthsync.app.ui.theme.ColorSuccessBg
import com.healthsync.app.ui.theme.ColorSuccessGreen
import com.healthsync.app.ui.theme.ColorTextMuted
import com.healthsync.app.ui.theme.ColorTextPrimary
import com.healthsync.app.ui.theme.ColorTextSecondary
import com.healthsync.app.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    onNavigateToPairing: () -> Unit = {},
    onNavigateToActivity: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToVitalTrack: () -> Unit = {}
) {
    val dashboardViewModel: DashboardViewModel = viewModel()
    val uiState by dashboardViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        dashboardViewModel.clearNetworkError()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBgPage)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            BrandLogo(name = "Dialitech", tagline = "Panel principal", size = BrandLogoSize.SM)

            Spacer(modifier = Modifier.height(20.dp))

            AppCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(ColorPrimary.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Watch,
                            contentDescription = "Watch",
                            tint = ColorPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Dialitech Watch Pro",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = ColorTextPrimary
                        )
                        Text(
                            text = "Conectado vía Bluetooth",
                            fontSize = 12.sp,
                            color = ColorSuccessGreen
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(9999.dp))
                            .background(ColorSuccessBg)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(ColorSuccessGreen)
                            )
                            Text(
                                text = "En línea",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = ColorSuccessGreen
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DashboardQuickAction(
                    icon = Icons.Default.MonitorHeart,
                    label = "Actividad",
                    value = "Ver métricas",
                    onClick = onNavigateToActivity,
                    modifier = Modifier.weight(1f)
                )
                DashboardQuickAction(
                    icon = Icons.Default.Favorite,
                    label = "Signos Vitales",
                    value = "Monitoreo",
                    onClick = onNavigateToVitalTrack,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DashboardQuickAction(
                    icon = Icons.Default.Person,
                    label = "Perfil",
                    value = "Ver perfil",
                    onClick = onNavigateToProfile,
                    modifier = Modifier.weight(1f)
                )
                DashboardQuickAction(
                    icon = Icons.Default.Watch,
                    label = "Dispositivos",
                    value = "Emparejar",
                    onClick = onNavigateToPairing,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            AppCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Resumen de Hoy",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = ColorTextPrimary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ResumenRow(
                        "Frecuencia cardíaca",
                        uiState.heartRate ?: "-- lpm",
                        if (uiState.heartRate != null) "Normal" else "Sin datos"
                    )
                    ResumenRow(
                        "Presión arterial",
                        "--/-- mmHg",
                        "Sin datos"
                    )
                    ResumenRow(
                        "Saturación O2",
                        uiState.oxygenSaturation?.let { "$it%" } ?: "--%",
                        if (uiState.oxygenSaturation != null) "Normal" else "Sin datos"
                    )
                    ResumenRow(
                        "Peso",
                        "-- kg",
                        "Sin datos"
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            val networkError = uiState.networkError
            if (networkError != null) {
                Text(
                    text = networkError,
                    fontSize = 12.sp,
                    color = ColorTextMuted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            } else if (uiState.pairingCode != null) {
                Text(
                    text = "Los datos se actualizarán cuando el dispositivo esté sincronizado.",
                    fontSize = 12.sp,
                    color = ColorTextMuted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }
        }

        BottomNavBar(
        activeRoute = Routes.Dashboard.route,
        onItemClick = { route ->
            when (route) {
                Routes.QrPairingFlow.route -> onNavigateToPairing()
                Routes.Activity.route -> onNavigateToActivity()
                Routes.Profile.route -> onNavigateToProfile()
                else -> Unit
            }
        }
    )
    }
}

@Composable
private fun DashboardQuickAction(
    icon: ImageVector,
    label: String,
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = ColorPrimary,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = ColorTextPrimary
            )
            Text(
                text = value,
                fontSize = 11.sp,
                color = ColorTextMuted
            )
        }
    }
}

@Composable
private fun ResumenRow(label: String, value: String, subtitle: String) {
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
            color = ColorTextMuted
        )
    }
}
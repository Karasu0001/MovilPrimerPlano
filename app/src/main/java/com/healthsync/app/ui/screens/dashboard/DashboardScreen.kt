package com.healthsync.app.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.healthsync.app.navigation.Routes
import com.healthsync.app.ui.components.AppCard
import com.healthsync.app.ui.components.BottomNavBar
import com.healthsync.app.ui.theme.ColorBgPage
import com.healthsync.app.ui.theme.ColorErrorRed
import com.healthsync.app.ui.theme.ColorPrimary
import com.healthsync.app.ui.theme.ColorTextSecondary
import com.healthsync.app.ui.theme.ColorTextMuted
import com.healthsync.app.viewmodel.CaregiverDashboardViewModel

@Composable
fun DashboardScreen(
    onNavigateToPairing: () -> Unit = {},
    onNavigateToActivity: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToVitalTrack: () -> Unit = {},
    onNavigateToPatientDetail: (String) -> Unit = {},
    onNavigateToCreatePatient: () -> Unit = {},
    onNavigateToAlerts: () -> Unit = {},
    viewModel: CaregiverDashboardViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadDashboard()
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                activeRoute = Routes.Dashboard.route,
                onItemClick = { route ->
                    when (route) {
                        Routes.Dashboard.route -> Unit
                        Routes.Alerts.route -> onNavigateToAlerts()
                        Routes.Profile.route -> onNavigateToProfile()
                    }
                }
            )
        },
        floatingActionButton = {
            if (!uiState.isLoading && uiState.networkError == null && uiState.patients.isNotEmpty()) {
                FloatingActionButton(
                    onClick = onNavigateToCreatePatient,
                    containerColor = ColorPrimary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Crear paciente", tint = Color.White)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ColorBgPage)
                .padding(innerPadding)
        ) {
            TopBar(onAlertsClick = onNavigateToAlerts)

            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Cargando pacientes...",
                        fontSize = 14.sp,
                        color = ColorTextSecondary
                    )
                }
            } else {
                val networkError = uiState.networkError
                if (networkError != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = networkError,
                                fontSize = 14.sp,
                                color = ColorErrorRed,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = { viewModel.loadDashboard() },
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Reintentar", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }
                    }
                } else if (uiState.patients.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = ColorTextMuted,
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "No hay pacientes vinculados",
                                fontSize = 16.sp,
                                color = ColorTextSecondary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Cre\u00e1 un paciente para comenzar",
                                fontSize = 13.sp,
                                color = ColorTextMuted
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Button(
                                onClick = onNavigateToCreatePatient,
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Crear mi primer paciente", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 12.dp)
                    ) {
                        item {
                            StatsRow(
                                totalPatients = uiState.totalPatients,
                                activeAlerts = uiState.activeAlerts
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        items(uiState.patients) { patient ->
                            AppCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onNavigateToPatientDetail(patient.patientId) }
                            ) {
                                PatientRow(patient = patient)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TopBar(onAlertsClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Mis Pacientes",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = ColorPrimary,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onAlertsClick) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Alertas",
                tint = ColorPrimary
            )
        }
    }
}

@Composable
private fun StatsRow(
    totalPatients: Int,
    activeAlerts: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatChip(
            value = totalPatients.toString(),
            label = "Pacientes",
            modifier = Modifier.weight(1f)
        )
        StatChip(
            value = activeAlerts.toString(),
            label = "Alertas",
            modifier = Modifier.weight(1f),
            alert = activeAlerts > 0
        )
    }
}

@Composable
private fun StatChip(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    alert: Boolean = false
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (alert) ColorErrorRed.copy(alpha = 0.1f) else ColorPrimary.copy(alpha = 0.1f))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = if (alert) ColorErrorRed else ColorPrimary
            )
            Text(
                text = label,
                fontSize = 11.sp,
                color = ColorTextSecondary
            )
        }
    }
}

@Composable
private fun PatientRow(patient: com.healthsync.app.network.response.PatientSummaryDto) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(22.dp))
                .background(ColorPrimary.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = ColorPrimary,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = patient.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = ColorPrimary
            )
            Text(
                text = "ID: ${patient.patientId}",
                fontSize = 12.sp,
                color = ColorTextSecondary
            )
        }
        if (patient.activeAlerts > 0) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = ColorErrorRed,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

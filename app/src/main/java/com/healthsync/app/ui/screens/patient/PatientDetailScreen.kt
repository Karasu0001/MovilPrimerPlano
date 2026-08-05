package com.healthsync.app.ui.screens.patient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.healthsync.app.ui.components.AppCard
import com.healthsync.app.ui.theme.ColorBgPage
import com.healthsync.app.ui.theme.ColorErrorRed
import com.healthsync.app.ui.theme.ColorPrimary
import com.healthsync.app.ui.theme.ColorTextSecondary
import com.healthsync.app.ui.theme.ColorTextMuted
import com.healthsync.app.viewmodel.PatientDetailViewModel

@Composable
fun PatientDetailScreen(
    patientId: String,
    onBack: () -> Unit = {}
) {
    val viewModel: PatientDetailViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadPatientDetail(patientId)
        viewModel.loadReadings(patientId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBgPage)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(ColorPrimary)
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = uiState.patientName ?: "Paciente",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (uiState.isLoading) {
                Text(
                    text = "Cargando...",
                    fontSize = 14.sp,
                    color = ColorTextSecondary,
                    modifier = Modifier.padding(32.dp)
                )
            } else {
                val networkError = uiState.networkError
                if (networkError != null) {
                    Text(
                        text = networkError,
                        fontSize = 14.sp,
                        color = ColorErrorRed,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { viewModel.loadPatientDetail(patientId) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Reintentar", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                } else {
                    AppCard(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            InfoRow(label = "Nombre", value = uiState.patientName)
                            InfoRow(label = "Edad", value = uiState.age?.toString())
                            InfoRow(label = "G\u00e9nero", value = uiState.gender)
                            InfoRow(label = "Notas", value = uiState.notes)
                            InfoRow(label = "Dispositivo", value = if (uiState.hasDevice) "Vinculado" else "No vinculado")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Generar c\u00f3digo de vinculaci\u00f3n",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = ColorPrimary,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { viewModel.generateCode(patientId) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ColorPrimary)
                    ) {
                        Text("Generar c\u00f3digo", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    uiState.generatedCode?.let { code ->
                        Spacer(modifier = Modifier.height(12.dp))
                        AppCard(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = code,
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ColorPrimary,
                                    letterSpacing = 4.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Vence en ${uiState.codeExpiresIn}s",
                                    fontSize = 12.sp,
                                    color = ColorTextSecondary
                                )
                            }
                        }
                    }

                    if (uiState.readings.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Lecturas recientes",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = ColorPrimary,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        uiState.readings.forEach { reading ->
                            AppCard(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = reading.timestamp,
                                        fontSize = 11.sp,
                                        color = ColorTextMuted
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    ReadingRow("Frecuencia card\u00edaca", "${reading.heartRate} lpm")
                                    ReadingRow("Saturaci\u00f3n O2", "${reading.oxygen}%")
                                    ReadingRow("Actividad", reading.activity.toString())
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = ColorTextSecondary,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value ?: "-",
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = ColorPrimary
        )
    }
}

@Composable
private fun ReadingRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
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
            fontWeight = FontWeight.Medium,
            color = ColorTextMuted
        )
    }
}
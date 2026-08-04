package com.healthsync.app.ui.screens.pairing.code

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.healthsync.app.ui.components.BottomNavBar
import com.healthsync.app.ui.theme.ColorBgPage
import com.healthsync.app.ui.theme.ColorBorder
import com.healthsync.app.ui.theme.ColorPrimary
import com.healthsync.app.ui.theme.ColorSuccessGreen
import com.healthsync.app.ui.theme.ColorTextMuted
import com.healthsync.app.ui.theme.ColorTextPrimary
import com.healthsync.app.ui.theme.ColorTextSecondary

@Composable
fun BuscandoDispositivoScreen(
    onConnect: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "bluetooth")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ColorBgPage)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Dialitech",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = ColorTextPrimary
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Bluetooth animado
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .alpha(pulseAlpha)
                    .clip(RoundedCornerShape(40.dp))
                    .background(ColorPrimary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "\uD83D\uDDF1\uFE0F", // Bluetooth emoji placeholder
                    fontSize = 36.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Buscando dispositivos",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = ColorTextPrimary
            )
            Text(
                text = "Buscando dispositivos mediante Bluetooth...",
                fontSize = 14.sp,
                color = ColorTextSecondary
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Dispositivos encontrados
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "DISPOSITIVOS ENCONTRADOS (1)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ColorTextSecondary
                )
                Text(text = "+", fontSize = 20.sp, color = ColorPrimary)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Card de dispositivo
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .border(1.dp, ColorBorder, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(ColorPrimary.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("\u231A", fontSize = 24.sp) // watch emoji
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Dialitech Watch Pro",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = ColorTextPrimary
                        )
                        Text(
                            text = "Modelo: DL-W3",
                            fontSize = 12.sp,
                            color = ColorTextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(ColorSuccessGreen)
                        )
                        Text("Señal Excelente", fontSize = 12.sp, color = ColorTextSecondary)
                    }
                    Text("Batería 85%", fontSize = 12.sp, color = ColorTextSecondary)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onConnect,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ColorPrimary)
                ) {
                    Text(
                        "Conectar",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ayuda
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .border(1.dp, ColorBorder, RoundedCornerShape(12.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "?",
                    fontSize = 14.sp,
                    color = ColorTextMuted,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "¿No ves tu dispositivo? Asegúrate que esté encendido y en modo emparejamiento.",
                    fontSize = 12.sp,
                    color = ColorTextSecondary
                )
            }
        }

        BottomNavBar(activeRoute = "pairing/code/search")
    }
}

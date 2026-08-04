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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.healthsync.app.ui.theme.ColorBgPage
import com.healthsync.app.ui.theme.ColorBorder
import com.healthsync.app.ui.theme.ColorErrorBg
import com.healthsync.app.ui.theme.ColorErrorRed
import com.healthsync.app.ui.theme.ColorInputBg
import com.healthsync.app.ui.theme.ColorPrimary
import com.healthsync.app.ui.theme.ColorTextPrimary
import com.healthsync.app.ui.theme.ColorTextSecondary

@Composable
fun VinculacionCanceladaScreen(
    onRetry: () -> Unit,
    onGoHome: () -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: String? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ColorBgPage)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Vinculación Cancelada",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = ColorTextPrimary
            )
            Text("X", fontSize = 18.sp, color = ColorTextSecondary)
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(ColorPrimary.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("\uD83D\uDCF1", fontSize = 28.sp)
                }
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(ColorPrimary.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("\u231A", fontSize = 28.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "El proceso fue cancelado",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = ColorTextPrimary
            )

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(ColorErrorBg)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "\u26A0",
                        fontSize = 16.sp,
                        color = ColorErrorRed
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = errorMessage,
                        fontSize = 13.sp,
                        color = ColorErrorRed,
                        textAlign = TextAlign.Start
                    )
                }
            } else {
                Text(
                    text = "Parece que la solicitud fue rechazada desde el smartwatch. Por favor, intenta de nuevo asegurándote de aceptar la solicitud en ambos dispositivos.",
                    fontSize = 14.sp,
                    color = ColorTextSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(9999.dp))
                    .background(ColorErrorBg)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(ColorErrorRed)
                )
                Text(
                    text = "Estado: Desincronizado",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ColorErrorRed
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onRetry,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ColorPrimary)
            ) {
                Text(
                    text = "Reintentar",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onGoHome,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = ColorTextPrimary
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, ColorBorder)
            ) {
                Text(
                    text = "Volver al inicio",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
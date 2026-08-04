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
import androidx.compose.material3.CircularProgressIndicator
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
import com.healthsync.app.ui.theme.ColorCodeBoxBg
import com.healthsync.app.ui.theme.ColorCodeText
import com.healthsync.app.ui.theme.ColorErrorBg
import com.healthsync.app.ui.theme.ColorErrorRed
import com.healthsync.app.ui.theme.ColorPhoneHeaderBg
import com.healthsync.app.ui.theme.ColorPrimary
import com.healthsync.app.ui.theme.ColorTextPrimary
import com.healthsync.app.ui.theme.ColorTextSecondary

@Composable
fun ConfirmarEmparejamientoScreen(
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
    pairingCode: String = "458 921",
    isLoading: Boolean = false,
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
                .background(ColorPhoneHeaderBg)
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Emparejamiento",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = ColorTextPrimary
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(RoundedCornerShape(48.dp))
                        .background(ColorPrimary.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("\u231A", fontSize = 40.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Verifica que el mismo código aparezca en tu smartwatch.",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = ColorTextPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(ColorCodeBoxBg)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "CÓDIGO DE VINCULACIÓN",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ColorTextSecondary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = pairingCode,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorCodeText,
                    letterSpacing = 8.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (errorMessage != null) {
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
                Spacer(modifier = Modifier.height(16.dp))
            }

            Text(
                text = "Este código garantiza que se esté conectando al dispositivo correcto para el monitoreo de tu salud.",
                fontSize = 14.sp,
                color = ColorTextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onConfirm,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ColorPrimary),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Confirmar",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onCancel,
                modifier = Modifier.height(44.dp),
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text(text = "Cancelar", fontSize = 14.sp, color = ColorTextSecondary)
            }
        }
    }
}
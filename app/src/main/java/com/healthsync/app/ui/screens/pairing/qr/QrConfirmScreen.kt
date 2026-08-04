package com.healthsync.app.ui.screens.pairing.qr

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
import com.healthsync.app.ui.theme.ColorCodeBoxBg
import com.healthsync.app.ui.theme.ColorCodeText
import com.healthsync.app.ui.theme.ColorPhoneHeaderBg
import com.healthsync.app.ui.theme.ColorPrimary
import com.healthsync.app.ui.theme.ColorSuccessGreen
import com.healthsync.app.ui.theme.ColorTextPrimary
import com.healthsync.app.ui.theme.ColorTextSecondary

@Composable
fun QrConfirmScreen(
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
    deviceName: String = "Dialitech Watch Pro",
    scannedCode: String = ""
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
                text = "Confirmar Dispositivo",
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
                    .size(80.dp)
                    .clip(RoundedCornerShape(40.dp))
                    .background(ColorSuccessGreen.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "\u2713", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = ColorSuccessGreen)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Dispositivo Detectado",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = ColorTextPrimary
            )
            Text(
                text = "Se ha identificado el siguiente dispositivo mediante el código QR.",
                fontSize = 14.sp,
                color = ColorTextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
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
                    text = "DISPOSITIVO",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ColorTextSecondary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = deviceName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorCodeText
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Código: ${scannedCode.take(16)}...",
                    fontSize = 11.sp,
                    color = ColorTextSecondary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onConfirm,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ColorPrimary)
            ) {
                Text(
                    text = "Confirmar y Vincular",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onCancel,
                modifier = Modifier.height(44.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text(text = "Cancelar", fontSize = 14.sp, color = ColorTextSecondary)
            }
        }
    }
}

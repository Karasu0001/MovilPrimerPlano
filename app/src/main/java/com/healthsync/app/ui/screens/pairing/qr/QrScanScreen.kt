package com.healthsync.app.ui.screens.pairing.qr

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
import com.healthsync.app.ui.theme.ColorPhoneHeaderBg
import com.healthsync.app.ui.theme.ColorPrimary
import com.healthsync.app.ui.theme.ColorTextMuted
import com.healthsync.app.ui.theme.ColorTextPrimary
import com.healthsync.app.ui.theme.ColorTextSecondary

@Composable
fun QrScanScreen(
    onQrScanned: (String) -> Unit,
    onSimulateScan: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
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
                text = "Escaneo QR",
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
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Black.copy(alpha = 0.05f))
                    .border(2.dp, ColorPrimary, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "\uD83D\uDCF7", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Vista previa de cámara",
                        fontSize = 12.sp,
                        color = ColorTextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Apunta la cámara al código QR",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = ColorTextPrimary
            )
            Text(
                text = "Enfoca el código QR que se muestra en tu Dialitech Watch Pro.",
                fontSize = 14.sp,
                color = ColorTextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "¿No puedes escanear?",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = ColorTextSecondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onSimulateScan,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ColorPrimary)
            ) {
                Text(
                    text = "Simular escaneo",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Pulsa este botón para simular el escaneo del QR (modo desarrollo).",
                fontSize = 11.sp,
                color = ColorTextMuted,
                textAlign = TextAlign.Center
            )
        }
    }
}

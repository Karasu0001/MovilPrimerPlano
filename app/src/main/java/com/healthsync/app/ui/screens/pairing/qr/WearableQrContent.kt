package com.healthsync.app.ui.screens.pairing.qr

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.healthsync.app.ui.theme.ColorErrorRed
import com.healthsync.app.ui.theme.ColorSuccessBg
import com.healthsync.app.ui.theme.ColorPrimary
import com.healthsync.app.ui.theme.ColorSuccessGreen
import com.healthsync.app.ui.theme.ColorTextPrimary
import com.healthsync.app.ui.theme.ColorTextSecondary
import com.healthsync.app.ui.theme.ColorWearableBg
import com.healthsync.app.ui.theme.ColorWearableRingAccentBlue
import com.healthsync.app.ui.theme.ColorWearableRingAccentTeal
import com.healthsync.app.ui.theme.ColorWearableSuccessBg
import com.healthsync.app.ui.theme.ColorWearableText
import com.healthsync.app.ui.theme.ColorWearableTextMuted
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun WearableShowQrContent(
    qrData: String,
    modifier: Modifier = Modifier
) {
    var qrBitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(qrData) {
        qrBitmap = withContext(Dispatchers.Default) {
            generateQrBitmap(qrData, 256)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ColorWearableBg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "ESCANEA ESTE CÓDIGO",
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = ColorWearableTextMuted
        )
        Spacer(modifier = Modifier.height(8.dp))

        qrBitmap?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "QR Code",
                modifier = Modifier.size(140.dp)
            )
        } ?: CircularProgressIndicator(
            modifier = Modifier.size(140.dp),
            color = ColorWearableRingAccentTeal,
            strokeWidth = 3.dp
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Dialitech Watch Pro",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = ColorWearableText
        )
        Text(
            text = "Esperando escaneo...",
            fontSize = 10.sp,
            color = ColorWearableTextMuted
        )
    }
}

@Composable
fun WearableQrConfirmContent(
    onAccept: () -> Unit,
    onReject: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ColorWearableBg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "VINCULAR CON",
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = ColorWearableTextMuted
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Dialitech App",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = ColorWearableText
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onAccept,
            shape = RoundedCornerShape(9999.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ColorWearableRingAccentTeal
            ),
            modifier = Modifier.height(36.dp).width(140.dp)
        ) {
            Text(
                text = "Aceptar",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = ColorWearableText
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Rechazar",
            fontSize = 11.sp,
            color = ColorWearableTextMuted,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun WearableQrSyncingContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ColorWearableBg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(56.dp),
            color = ColorWearableRingAccentBlue,
            strokeWidth = 4.dp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Vinculando...",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = ColorWearableText
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "QR escaneado correctamente",
            fontSize = 11.sp,
            color = ColorWearableTextMuted
        )
    }
}

@Composable
fun WearableQrSuccessContent(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ColorWearableSuccessBg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(ColorSuccessBg),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "\u2713",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = ColorSuccessGreen
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "¡Dispositivo Vinculado!",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = ColorTextPrimary
        )
        Text(
            text = "Conexión QR completada.",
            fontSize = 12.sp,
            color = ColorTextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onDismiss,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ColorPrimary),
            modifier = Modifier.height(40.dp)
        ) {
            Text(
                text = "Entendido",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun WearableQrCancelledContent(
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ColorWearableBg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(ColorErrorRed.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "X",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = ColorErrorRed
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "VINCULACIÓN RECHAZADA",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = ColorErrorRed
        )
        Text(
            text = "Has cancelado la vinculación por QR.",
            fontSize = 12.sp,
            color = ColorWearableTextMuted,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Cerrar",
            fontSize = 12.sp,
            color = ColorWearableTextMuted,
            modifier = Modifier
                .clip(RoundedCornerShape(9999.dp))
                .background(Color.White.copy(alpha = 0.1f))
                .padding(horizontal = 24.dp, vertical = 8.dp)
        )
    }
}

private fun generateQrBitmap(content: String, size: Int): Bitmap? {
    return try {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, size, size)
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)
        for (x in 0 until size) {
            for (y in 0 until size) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt())
            }
        }
        bitmap
    } catch (e: Exception) {
        null
    }
}

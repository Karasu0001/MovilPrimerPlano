package com.healthsync.app.ui.screens.pairing.code

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.healthsync.app.ui.theme.ColorWearableBg
import com.healthsync.app.ui.theme.ColorWearableRingAccentTeal
import com.healthsync.app.ui.theme.ColorWearableText
import com.healthsync.app.ui.theme.ColorWearableTextMuted

@Composable
fun WearableConfirmarCodigoContent(
    pairingCode: String = "458 921",
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
            text = "CONFIRMA EL CÓDIGO",
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = ColorWearableTextMuted
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = pairingCode,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = ColorWearableText,
            letterSpacing = 8.sp
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
                text = "Confirmar",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = ColorWearableText
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Cancelar",
            fontSize = 11.sp,
            color = ColorWearableTextMuted,
            modifier = Modifier.padding(8.dp)
        )
    }
}

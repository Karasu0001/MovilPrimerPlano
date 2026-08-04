package com.healthsync.app.ui.screens.pairing.code

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.healthsync.app.ui.theme.ColorWearableBg
import com.healthsync.app.ui.theme.ColorWearableRingAccentTeal
import com.healthsync.app.ui.theme.ColorWearableText
import com.healthsync.app.ui.theme.ColorWearableTextMuted

@Composable
fun WearableSincronizarAhoraContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ColorWearableBg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Corazón teal grande
        Text(
            text = "\u2665",
            fontSize = 64.sp,
            color = ColorWearableRingAccentTeal
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Dialitech",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = ColorWearableText
        )
        Text(
            text = "Preparado para enviar datos al móvil.",
            fontSize = 11.sp,
            color = ColorWearableTextMuted,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Botón circular sync
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(ColorWearableRingAccentTeal.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text("\u21BB", fontSize = 24.sp, color = ColorWearableRingAccentTeal)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "SINCRONIZAR AHORA",
                fontSize = 9.sp,
                fontWeight = FontWeight.SemiBold,
                color = ColorWearableTextMuted
            )
        }
    }
}

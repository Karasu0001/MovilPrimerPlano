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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.healthsync.app.ui.theme.ColorWearableBg
import com.healthsync.app.ui.theme.ColorWearableRingAccentBlue
import com.healthsync.app.ui.theme.ColorWearableText
import com.healthsync.app.ui.theme.ColorWearableTextMuted

@Composable
fun WearableVinculandoContent(
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
        Box(
            modifier = Modifier
                .size(6.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Samsung Galaxy S...",
                fontSize = 11.sp,
                color = ColorWearableTextMuted,
                maxLines = 1
            )
        }
    }
}

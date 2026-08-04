package com.healthsync.app.ui.screens.pairing.code

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.healthsync.app.ui.theme.ColorErrorRed
import com.healthsync.app.ui.theme.ColorWearableBg
import com.healthsync.app.ui.theme.ColorWearableTextMuted

@Composable
fun WearableSolicitudRechazadaContent(
    onClose: () -> Unit,
    errorMessage: String? = null,
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
            text = "SOLICITUD RECHAZADA",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = ColorErrorRed
        )

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                fontSize = 11.sp,
                color = ColorWearableTextMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        } else {
            Text(
                text = "Has cancelado la vinculación.",
                fontSize = 12.sp,
                color = ColorWearableTextMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Cerrar",
            fontSize = 12.sp,
            color = ColorWearableTextMuted,
            modifier = Modifier
                .clip(RoundedCornerShape(9999.dp))
                .background(androidx.compose.ui.graphics.Color.White.copy(alpha = 0.1f))
                .padding(horizontal = 24.dp, vertical = 8.dp)
        )
    }
}
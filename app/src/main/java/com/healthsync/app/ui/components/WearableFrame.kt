package com.healthsync.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.healthsync.app.ui.theme.ColorWearableBg
import com.healthsync.app.ui.theme.ColorWearableText
import com.healthsync.app.ui.theme.ColorWearableTextMuted
import com.healthsync.app.ui.theme.HealthSyncRadii

@Composable
fun WearableFrame(
    theme: WearableTheme = WearableTheme.Dark,
    label: String? = null,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val bgColor = if (theme == WearableTheme.Dark) ColorWearableBg else Color(0xFFE7F3F8)
    val textColor = if (theme == WearableTheme.Dark) ColorWearableText else ColorWearableText

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (label != null) {
            Text(
                text = label,
                color = ColorWearableTextMuted,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }

        Box(
            modifier = Modifier
                .size(280.dp)
                .aspectRatio(1f)
                .shadow(16.dp, RoundedCornerShape(40.dp))
                .background(
                    color = bgColor,
                    shape = HealthSyncRadii.wearable
                )
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

enum class WearableTheme { Dark, Light }

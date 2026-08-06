package com.healthsync.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.healthsync.app.ui.theme.ColorLogoBg
import com.healthsync.app.ui.theme.ColorTextPrimary
import com.healthsync.app.ui.theme.ColorTextSecondary

@Composable
fun BrandLogo(
    name: String,
    tagline: String? = null,
    size: BrandLogoSize = BrandLogoSize.MD,
    modifier: Modifier = Modifier
) {
    val iconSize = when (size) {
        BrandLogoSize.SM -> 32.dp
        BrandLogoSize.MD -> 44.dp
    }
    val iconPadding = when (size) {
        BrandLogoSize.SM -> 6.dp
        BrandLogoSize.MD -> 10.dp
    }
    val textSize = when (size) {
        BrandLogoSize.SM -> 18.sp
        BrandLogoSize.MD -> 20.sp
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(iconSize)
                    .clip(RoundedCornerShape(12.dp))
                    .background(ColorLogoBg),
                contentAlignment = Alignment.Center
            ) {
                HealthPulseIcon(
                    modifier = Modifier.size(iconSize * 0.7f),
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = name,
                color = ColorTextPrimary,
                fontSize = textSize,
                fontWeight = FontWeight.Bold
            )
        }
        if (tagline != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = tagline,
                color = ColorTextSecondary,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(260.dp)
            )
        }
    }
}

enum class BrandLogoSize { SM, MD }

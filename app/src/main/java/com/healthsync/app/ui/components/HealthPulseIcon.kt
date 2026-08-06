package com.healthsync.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import com.healthsync.app.ui.theme.ColorLogoBg

@Composable
fun HealthPulseIcon(
    modifier: Modifier = Modifier,
    tint: Color = Color.White
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        val heart = Path().apply {
            moveTo(w * 0.5f, h * 0.82f)
            cubicTo(w * 0.05f, h * 0.55f, w * 0.05f, h * 0.15f, w * 0.5f, h * 0.32f)
            cubicTo(w * 0.95f, h * 0.15f, w * 0.95f, h * 0.55f, w * 0.5f, h * 0.82f)
            close()
        }
        drawPath(heart, color = tint.copy(alpha = 0.9f))

        val pulseY = h * 0.5f
        val pulse = Path().apply {
            moveTo(w * 0.08f, pulseY)
            lineTo(w * 0.32f, pulseY)
            lineTo(w * 0.42f, pulseY - h * 0.22f)
            lineTo(w * 0.52f, pulseY + h * 0.28f)
            lineTo(w * 0.60f, pulseY)
            lineTo(w * 0.92f, pulseY)
        }
        drawPath(
            pulse,
            color = ColorLogoBg,
            style = Stroke(
                width = w * 0.05f,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )
    }
}

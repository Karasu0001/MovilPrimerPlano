package com.healthsync.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import com.healthsync.app.ui.theme.ColorAuthGradientEnd
import com.healthsync.app.ui.theme.ColorAuthGradientStart

@Composable
fun AuthWaveBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(ColorAuthGradientStart, ColorAuthGradientEnd)
                )
            )
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val w = size.width
            val h = size.height

            val wavePath = Path().apply {
                moveTo(0f, h * 0.78f)
                cubicTo(
                    w * 0.25f, h * 0.68f,
                    w * 0.50f, h * 0.90f,
                    w * 0.75f, h * 0.78f
                )
                cubicTo(
                    w * 0.90f, h * 0.72f,
                    w, h * 0.82f,
                    w, h * 0.78f
                )
                lineTo(w, h)
                lineTo(0f, h)
                close()
            }
            drawPath(wavePath, Color.White.copy(alpha = 0.08f), style = Fill)

            val wave2Path = Path().apply {
                moveTo(0f, h * 0.85f)
                cubicTo(
                    w * 0.30f, h * 0.76f,
                    w * 0.60f, h * 0.95f,
                    w, h * 0.85f
                )
                lineTo(w, h)
                lineTo(0f, h)
                close()
            }
            drawPath(wave2Path, Color.White.copy(alpha = 0.05f), style = Fill)
        }

        content()
    }
}

package com.healthsync.app.ui.screens.pairing.code

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.healthsync.app.ui.theme.ColorBgPage
import com.healthsync.app.ui.theme.ColorPhoneHeaderBg
import com.healthsync.app.ui.theme.ColorPrimary
import com.healthsync.app.ui.theme.ColorTextMuted
import com.healthsync.app.ui.theme.ColorTextPrimary
import com.healthsync.app.ui.theme.ColorTextSecondary
import kotlinx.coroutines.delay

@Composable
fun SincronizandoDispositivoScreen(
    onComplete: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var progress by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        for (i in 0..100) {
            progress = i
            delay(35)
        }
        delay(400)
        onComplete()
    }

    val animatedProgress by animateFloatAsState(
        targetValue = progress / 100f,
        animationSpec = tween(100),
        label = "progress"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ColorBgPage)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(ColorPhoneHeaderBg)
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Dialitech",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = ColorTextPrimary
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sincronizando Dispositivo",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = ColorTextPrimary
            )
            Text(
                text = "Vinculando el dispositivo mediante Bluetooth...",
                fontSize = 14.sp,
                color = ColorTextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Progreso circular
            Box(
                modifier = Modifier.size(128.dp),
                contentAlignment = Alignment.Center
            ) {
                // Círculo de fondo
                val strokeWidth = 8.dp
                androidx.compose.material3.CircularProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier.size(128.dp),
                    color = ColorPrimary,
                    trackColor = Color(0xFFE5E7EB),
                    strokeWidth = strokeWidth,
                )
                Text(
                    text = "${progress}%",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Transfiriendo datos biométricos...",
                fontSize = 14.sp,
                color = ColorTextSecondary
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Cancelar",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFDC2626),
                modifier = Modifier.padding(vertical = 8.dp).clickable { onCancel() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Mantén tu reloj cerca al teléfono para evitar interrupciones en la sincronización.",
                fontSize = 12.sp,
                color = ColorTextMuted,
                textAlign = TextAlign.Center
            )
        }
    }
}

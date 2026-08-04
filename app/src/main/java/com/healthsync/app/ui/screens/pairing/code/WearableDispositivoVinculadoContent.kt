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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.healthsync.app.ui.theme.ColorPrimary
import com.healthsync.app.ui.theme.ColorSuccessBg
import com.healthsync.app.ui.theme.ColorSuccessGreen
import com.healthsync.app.ui.theme.ColorTextPrimary
import com.healthsync.app.ui.theme.ColorTextSecondary
import com.healthsync.app.ui.theme.ColorWearableSuccessBg

@Composable
fun WearableDispositivoVinculadoContent(
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
            text = "Ahora puedes monitorear tu salud.",
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
                color = androidx.compose.ui.graphics.Color.White
            )
        }
    }
}

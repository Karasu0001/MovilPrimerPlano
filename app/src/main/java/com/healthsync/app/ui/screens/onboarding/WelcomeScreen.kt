package com.healthsync.app.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.healthsync.app.ui.components.BrandLogo
import com.healthsync.app.ui.components.BrandLogoSize
import com.healthsync.app.ui.theme.ColorBadgeHelpBg
import com.healthsync.app.ui.theme.ColorBgPage
import com.healthsync.app.ui.theme.ColorBorder
import com.healthsync.app.ui.theme.ColorFooterBadgeBorder
import com.healthsync.app.ui.theme.ColorHeroGradientEnd
import com.healthsync.app.ui.theme.ColorHeroGradientStart
import com.healthsync.app.ui.theme.ColorPrimary
import com.healthsync.app.ui.theme.ColorTextPrimary
import com.healthsync.app.ui.theme.ColorTextSecondary

@Composable
fun WelcomeScreen(
    onStart: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onPairingMethod: () -> Unit = {},
    onHelpClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBgPage)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        // Hero visual
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(ColorHeroGradientStart, ColorHeroGradientEnd)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            // Placeholder ilustración — TODO: reemplazar por asset de marketing
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White.copy(alpha = 0.6f))
                )
                Box(
                    modifier = Modifier
                        .width(128.dp)
                        .height(12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White.copy(alpha = 0.4f))
                )
                Box(
                    modifier = Modifier
                        .width(96.dp)
                        .height(12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White.copy(alpha = 0.3f))
                )
            }

            // Foto circular paciente — TODO: reemplazar por asset real
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = 40.dp)
                    .size(80.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(Color(0xFFD1D5DB))
                    .border(4.dp, Color.White, androidx.compose.foundation.shape.CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(64.dp)) // compensar foto superpuesta

        // BrandLogo
        BrandLogo(name = "Dialitech", size = BrandLogoSize.SM)

        Spacer(modifier = Modifier.height(20.dp))

        // Headline
        Text(
            text = "Tu salud, monitoreada en tiempo real.",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = ColorTextPrimary,
            lineHeight = 32.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Body
        Text(
            text = "Seguro, simple y siempre conectado. Experimenta un acompañamiento diseñado específicamente para tu camino en la diálisis.",
            fontSize = 14.sp,
            color = ColorTextSecondary,
            lineHeight = 21.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Comenzar
        Button(
            onClick = onStart,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ColorPrimary)
        ) {
            Text(
                text = "Comenzar",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Link Iniciar sesión
        Text(
            text = "Iniciar sesión",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = ColorPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onLoginClick() }
                .padding(vertical = 12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Link Vincular con código
        Text(
            text = "Ya tengo un c\u00f3digo de vinculaci\u00f3n",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = ColorPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onPairingMethod() }
                .padding(vertical = 12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Badges de confianza
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BadgeItem(
                text = "Cumple con HIPAA",
                modifier = Modifier.weight(1f)
            )
            BadgeItem(
                text = "Monitoreo 24/7",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun BadgeItem(text: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .border(1.dp, ColorFooterBadgeBorder, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(ColorPrimary.copy(alpha = 0.1f))
        )
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF374151)
        )
    }
}

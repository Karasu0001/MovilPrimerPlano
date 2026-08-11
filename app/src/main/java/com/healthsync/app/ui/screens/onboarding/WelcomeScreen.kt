package com.healthsync.app.ui.screens.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.healthsync.app.ui.components.BrandLogo
import com.healthsync.app.ui.components.BrandLogoSize
import com.healthsync.app.ui.theme.ColorBgPage
import com.healthsync.app.ui.theme.ColorFooterBadgeBorder
import com.healthsync.app.ui.theme.ColorHeroGradientEnd
import com.healthsync.app.ui.theme.ColorHeroGradientStart
import com.healthsync.app.ui.theme.ColorPrimary
import com.healthsync.app.ui.theme.ColorTextPrimary
import com.healthsync.app.ui.theme.ColorTextSecondary

private val ColorRoleRed = Color(0xFFDC2626)

@Composable
fun WelcomeScreen(
    onStart: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onPairingMethod: () -> Unit = {}
) {
    var selectedRole by remember { mutableStateOf<String?>(null) }

    AnimatedContent(
        targetState = selectedRole,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "role_transition"
    ) { role ->
        when (role) {
            null -> RoleSelectionStep(
                onSelectCaregiver = { selectedRole = "caregiver" },
                onSelectPatient = { selectedRole = "patient" }
            )
            "caregiver" -> CaregiverStep(
                onBack = { selectedRole = null },
                onStart = onStart,
                onLoginClick = onLoginClick,
                onSwitchToPatient = { selectedRole = "patient" }
            )
            "patient" -> PatientStep(
                onBack = { selectedRole = null },
                onPairingMethod = onPairingMethod,
                onSwitchToCaregiver = { selectedRole = "caregiver" }
            )
        }
    }
}

@Composable
private fun RoleSelectionStep(
    onSelectCaregiver: () -> Unit,
    onSelectPatient: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBgPage)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(ColorHeroGradientStart, ColorHeroGradientEnd)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White.copy(alpha = 0.6f))
                )
                Box(
                    modifier = Modifier
                        .width(112.dp)
                        .height(10.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White.copy(alpha = 0.4f))
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        BrandLogo(name = "Dialitech", size = BrandLogoSize.SM)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "\u00bfC\u00f3mo quer\u00e9s usar Dialitech?",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = ColorTextPrimary,
            lineHeight = 30.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Eleg\u00ed tu perfil para continuar.",
            fontSize = 14.sp,
            color = ColorTextSecondary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

        RoleCard(
            icon = Icons.Default.Favorite,
            title = "Soy Paciente",
            subtitle = "Monitore\u00e1 tu salud en tiempo real.",
            accentColor = ColorRoleRed,
            onClick = onSelectPatient,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(14.dp))

        RoleCard(
            icon = Icons.Default.Person,
            title = "Soy Cuidador",
            subtitle = "Acced\u00e9 al monitoreo de tus pacientes.",
            accentColor = ColorPrimary,
            onClick = onSelectCaregiver,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(28.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BadgeItem(text = "Datos en tiempo real", modifier = Modifier.weight(1f))
            BadgeItem(text = "Monitoreo 24/7", modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun CaregiverStep(
    onBack: () -> Unit,
    onStart: () -> Unit,
    onLoginClick: () -> Unit,
    onSwitchToPatient: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBgPage)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = ColorTextPrimary
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Modo Cuidador",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = ColorPrimary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Acced\u00e9 al monitoreo de tus pacientes.",
            fontSize = 14.sp,
            color = ColorTextSecondary
        )

        Spacer(modifier = Modifier.height(32.dp))

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

        Text(
            text = "Iniciar sesi\u00f3n",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = ColorPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onLoginClick() }
                .padding(vertical = 12.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "\u00bfSos paciente? ",
                fontSize = 13.sp,
                color = ColorTextSecondary
            )
            Text(
                text = "Entr\u00e1 como paciente \u2192",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = ColorPrimary,
                modifier = Modifier.clickable { onSwitchToPatient() }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun PatientStep(
    onBack: () -> Unit,
    onPairingMethod: () -> Unit,
    onSwitchToCaregiver: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBgPage)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = ColorTextPrimary
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Modo Paciente",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = ColorRoleRed
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Monitore\u00e1 tu salud en tiempo real.",
            fontSize = 14.sp,
            color = ColorTextSecondary
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onPairingMethod,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ColorRoleRed)
        ) {
            Text(
                text = "Ya tengo un c\u00f3digo de vinculaci\u00f3n",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "\u00bfSos cuidador? ",
                fontSize = 13.sp,
                color = ColorTextSecondary
            )
            Text(
                text = "Entr\u00e1 como cuidador \u2192",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = ColorPrimary,
                modifier = Modifier.clickable { onSwitchToCaregiver() }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun RoleCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(accentColor.copy(alpha = 0.06f))
            .border(1.dp, accentColor.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(accentColor.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(28.dp)
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = ColorTextPrimary
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                fontSize = 13.sp,
                color = ColorTextSecondary
            )
        }
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

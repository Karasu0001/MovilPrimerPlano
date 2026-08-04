package com.healthsync.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// TODO: descargar Inter desde Google Fonts y colocar en res/font/
// Por ahora usamos la fuente por defecto del sistema (equivalente visual a Inter)
val InterFontFamily = FontFamily.Default

val HealthSyncTypography = Typography(
    // Código de vinculación 32px bold tracking-widest
    displayLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        letterSpacing = 4.sp
    ),
    // Headline 26px bold
    headlineLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        lineHeight = 32.sp
    ),
    // Título tarjeta 22px bold
    headlineMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    // Título 20px bold (marca)
    headlineSmall = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    // Título 18px bold
    titleLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    // Título 16px semibold → 16sp
    titleMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    // Subtítulos 14px
    titleSmall = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    // Body 14px regular
    bodyLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 21.sp
    ),
    // Body 13px regular
    bodyMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp
    ),
    // Label 13px semibold (labels de campo)
    bodySmall = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp
    ),
    // Labels pequeños 12px / 11px
    labelLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    labelMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        letterSpacing = 1.sp
    ),
    labelSmall = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp
    )
)

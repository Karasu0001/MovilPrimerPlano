package com.healthsync.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val HealthSyncLightColorScheme = lightColorScheme(
    primary = ColorPrimary,
    onPrimary = ColorBgCard,
    secondary = ColorTextSecondary,
    onSecondary = ColorBgCard,
    background = ColorBgPage,
    onBackground = ColorTextPrimary,
    surface = ColorBgCard,
    onSurface = ColorTextPrimary,
    surfaceVariant = ColorInputBg,
    onSurfaceVariant = ColorTextSecondary,
    outline = ColorBorder,
    outlineVariant = ColorDivider,
    error = ColorErrorRed,
    onError = ColorBgCard,
    errorContainer = ColorErrorBg,
    onErrorContainer = ColorErrorRed
)

@Composable
fun HealthSyncTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = HealthSyncLightColorScheme,
        typography = HealthSyncTypography,
        shapes = HealthSyncShapes,
        content = content
    )
}

package com.rumpilstilstkin.gloomhavenhelper.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val colors = darkColorScheme(
    primary = Color(0xFFB8882A),
    onPrimary = Color(0xFF422C00),
    primaryContainer = Color(0x05FFFFFF),
    onPrimaryContainer = Color(0xFFFFDEA9),

    // Secondary
    secondary = Color(0xFFDAC3A1),
    onSecondary = Color(0xFF3C2E16),
    secondaryContainer = Color(0x66000000),
    onSecondaryContainer = Color(0xFFFFE088),

    // Tertiary
    tertiary = Color(0xFFB4CEA6),
    onTertiary = Color(0xFF203619),
    tertiaryContainer = Color(0xFF364D2D),
    onTertiaryContainer = Color(0xFFCFEBC0),

    // Error
    error = Color(0xFF973B30),
    onError = Color(0xFF2E0305),
    errorContainer = Color(0xFF6B1C1C),
    onErrorContainer = Color(0xFFFFDAD6),

    // Базовые фоны
    background = Color(0xFF1A1C24),
    onBackground = Color(0xFFECE1D4),
    surface = Color(0xFF212C34),
    onSurface = Color(0xFFECE1D4),

    // Поверхности карточек и списков
    surfaceVariant = Color(0xFF0F1F18),
    onSurfaceVariant = Color(0xFF948C81),

    // Обводки и разделители
    outline = Color(0x66DEB44B),
    outlineVariant = Color(0xFF4E4639),

    // Затемнение
    scrim = Color(0xFF000000),
    surfaceTint = Color(0xFFEEBF6D)
)

@Composable
fun GloomhavenMasterTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
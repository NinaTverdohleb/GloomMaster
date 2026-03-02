package com.rumpilstilstkin.gloomhavenhelper.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val colors = darkColorScheme(
    primary = primary,
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = primaryContainer,
    onPrimaryContainer = Color(0xFFFFFFFF),

    // Secondary (Золотой)
    secondary = Green,
    onSecondary = Color(0xFFC5ECDF),
    secondaryContainer = secondaryContainer,
    onSecondaryContainer = Color(0xFFFFE088),

    // Tertiary (Приглушенный морской - для третьего уровня акцентов, например XP)
    tertiary = Color(0xFFA9CFC4),
    onTertiary = Color(0xFF10372F),
    tertiaryContainer = Color(0xFF284E45),
    onTertiaryContainer = Color(0xFFC5ECDF),

    // Error (Стандартный красный M3 для темной темы)
    error = Red,
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0x4493000A),
    onErrorContainer = Color(0xFFFFDAD6),

    // Базовые фоны (Нейтральные темно-зеленые оттенки)
    background = background,
    onBackground = onSurface,
    surface = surface,
    onSurface = onSurface,

    // Поверхности карточек и списков
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,

    // Обводки и разделители
    outline = Divider,
    outlineVariant = DividerVariant,

    // Инвертированные цвета (Снэкбары)
    inverseSurface = Color(0xFFE0E3E1),
    inverseOnSurface = Color(0xFF2D3130),
    inversePrimary = Color(0xFF006B5A),

    // Затемнение (Модальные окна, BottomSheet)
    scrim = Color(0xFF000000),
    surfaceTint = Color(0xFF55DBC0)
)

@Composable
fun GloomhavenHalperTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
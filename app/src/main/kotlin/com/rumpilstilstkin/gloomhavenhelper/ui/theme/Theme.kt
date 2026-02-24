package com.rumpilstilstkin.gloomhavenhelper.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val colors = darkColorScheme(
    primary = Color(0xFF00382E), // Тон 80: Текст, иконки на темном фоне
    onPrimary = Color(0xFFFFFFFF), // Тон 20: Иконки на primary фоне
    primaryContainer = Color(0xFF105D54), // Тон 30: Заливка кнопок (Continue Adventure)
    onPrimaryContainer = Color(0xFFFFFFFF), // Тон 90: Текст внутри кнопок

    // Secondary (Золотой)
    secondary = Color(0xFFE1C36B), // Тон 80: Акценты, FAB (+)
    onSecondary = Color(0xFF3D2E00), // Тон 20: Иконка внутри FAB
    secondaryContainer = Color(0xFF584400), // Тон 30: Выделенные состояния
    onSecondaryContainer = Color(0xFFFFE088), // Тон 90: Золотой текст (Заголовки, Золото)

    // Tertiary (Приглушенный морской - для третьего уровня акцентов, например XP)
    tertiary = Color(0xFFA9CFC4),
    onTertiary = Color(0xFF10372F),
    tertiaryContainer = Color(0xFF284E45),
    onTertiaryContainer = Color(0xFFC5ECDF),

    // Error (Стандартный красный M3 для темной темы)
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),

    // Базовые фоны (Нейтральные темно-зеленые оттенки)
    background = Background, // Тон 10: Самый темный фон (подложка приложения)
    onBackground = Color.White, // Тон 90: Основной текст
    surface = Background, // Тон 10: Совпадает с background
    onSurface = Color.White, // Тон 90: Основной текст на surface

    // Поверхности карточек и списков
    surfaceVariant = SurfaceVariant, // Тон 30: Фон карточек (Your Party, Chapter 4)
    onSurfaceVariant = OnSurfaceVariant, // Тон 80: Второстепенный текст (LVL, описания)

    // Обводки и разделители
    outline = Color(0xFF89938F), // Тон 50: Активные границы (фокус)
    outlineVariant = Color(0xFF3F4946), // Тон 30: Тонкие неактивные рамки карточек

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
package com.rumpilstilstkin.gloommaster.designsystem.icons

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.designsystem.R

enum class MagicIcon(
    @param:DrawableRes override val resId: Int,
    val tintColor: Color,
) : GloomIcon {
    Sun(R.drawable.ic_magic_sun, Color(0xFFC29240)),
    Moon(R.drawable.ic_magic_moon, Color(0xFF39596D)),
    Fire(R.drawable.ic_magic_fire, Color(0xFF903E3E)),
    Air(R.drawable.ic_magic_air, Color(0xFF2F4C81)),
    Frost(R.drawable.ic_magic_frost, Color(0xFF3B9FC6)),
    Earth(R.drawable.ic_magic_earth, Color(0xFF407538)),
    SpendSun(R.drawable.ic_magic_sun, Color.Unspecified),
    SpendMoon(R.drawable.ic_magic_moon, Color.Unspecified),
    SpendFire(R.drawable.ic_magic_fire, Color.Unspecified),
    SpendAir(R.drawable.ic_magic_air, Color.Unspecified),
    SpendFrost(R.drawable.ic_magic_frost, Color.Unspecified),
    SpendEarth(R.drawable.ic_magic_earth, Color.Unspecified),
    SpendAny(R.drawable.ic_magic_any_spend, Color.Unspecified),
    ;

    @Composable
    override fun painter(): Painter =
        when (this) {
            SpendSun -> crossedOutIconPainter(Sun)
            SpendMoon -> crossedOutIconPainter(Moon)
            SpendAir -> crossedOutIconPainter(Air)
            SpendFrost -> crossedOutIconPainter(Frost)
            SpendEarth -> crossedOutIconPainter(Earth)
            SpendFire -> crossedOutIconPainter(Fire)
            else -> super.painter()
        }
}

@Composable
private fun crossedOutIconPainter(
    icon: MagicIcon,
    crossColor: Color = Color(0xFF811111),
    strokeWidthDp: Dp = 2.dp,
): Painter {
    val basePainter = painterResource(id = icon.resId)
    val density = LocalDensity.current
    val strokeWidthPx = with(density) { strokeWidthDp.toPx() }

    return remember(basePainter, icon.tintColor, crossColor, strokeWidthPx) {
        object : Painter() {
            override val intrinsicSize: Size = basePainter.intrinsicSize

            override fun DrawScope.onDraw() {
                with(basePainter) {
                    draw(
                        size = size,
                        colorFilter = ColorFilter.tint(icon.tintColor),
                    )
                }
                drawLine(
                    color = crossColor,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, size.height),
                    strokeWidth = strokeWidthPx,
                    cap = StrokeCap.Round,
                )
                drawLine(
                    color = crossColor,
                    start = Offset(size.width, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = strokeWidthPx,
                    cap = StrokeCap.Round,
                )
            }
        }
    }
}

package com.rumpilstilstkin.gloommaster.screens.scenario.play.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.designsystem.icons.MagicIcon
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.ChargeLevel

@Composable
fun ChargeIcon(
    icon: MagicIcon,
    charge: ChargeLevel,
    assets: ChargeIconAssets,
    modifier: Modifier = Modifier,
    iconSize: Dp = 40.dp,
) {
    val padding = iconSize * 0.2f
    val totalSize = iconSize + padding * 2
    val energy = charge.level.toFloat()
    Box(
        modifier = modifier.size(totalSize),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.size(totalSize)) {
            val center = Offset(size.width / 2f, size.height / 2f)

            assets.glowBrushes[charge]?.let { brush ->
                val glowStrength = (energy / 2f).coerceIn(0f, 1f)
                val glowRadius = (iconSize.toPx() / 2f) * (1.5f + 0.1f * glowStrength)
                drawCircle(brush = brush, radius = glowRadius, center = center)
            }

            if (charge.level > 0) {
                drawCircle(
                    color = assets.ringGeometry.color,
                    radius = assets.ringGeometry.ring1Radius,
                    center = center,
                    style = Stroke(width = assets.ringGeometry.strokePx),
                )
            }

            if (charge.level > 1) {
                drawCircle(
                    color = assets.ringGeometry.color,
                    radius = assets.ringGeometry.ring2Radius,
                    center = center,
                    style = Stroke(width = assets.ringGeometry.strokePx),
                )
            }
        }

        Icon(
            painter = icon.painter(),
            contentDescription = null,
            tint = icon.tintColor,
            modifier = Modifier.size(iconSize),
        )
    }
}

@Composable
fun rememberChargeIconAssets(iconSize: Dp = 40.dp): ChargeIconAssets {
    val density = LocalDensity.current
    val glowColor = MaterialTheme.colorScheme.surfaceTint
    return remember(iconSize, density) {
        with(density) {
            val padding = iconSize * 0.2f
            val totalSizePx = (iconSize + padding * 2).toPx()
            val iconRadiusPx = iconSize.toPx() / 2f
            val center = Offset(totalSizePx / 2f, totalSizePx / 2f)
            val strokePx = (iconRadiusPx * 0.07f).coerceAtLeast(2f)

            val brushes =
                ChargeLevel.entries.associateWith { level ->
                    val glowStrength = (level.level.toFloat() / 2f).coerceIn(0f, 1f)
                    if (glowStrength > 0.01f) {
                        val glowRadius = iconRadiusPx * (1.5f + 0.1f * glowStrength)
                        val edgeStop = (iconRadiusPx / glowRadius).coerceIn(0f, 1f)
                        val peakStop = (edgeStop + (1f - edgeStop) * 0.25f).coerceIn(0f, 1f)
                        val glowAlpha = 0.3f * glowStrength
                        Brush.radialGradient(
                            colorStops =
                                arrayOf(
                                    0f to glowColor.copy(alpha = 0f),
                                    edgeStop to glowColor.copy(alpha = 0f),
                                    peakStop to glowColor.copy(alpha = glowAlpha),
                                    1f to glowColor.copy(alpha = 0f),
                                ),
                            center = center,
                            radius = glowRadius,
                        )
                    } else {
                        null
                    }
                }

            ChargeIconAssets(
                glowBrushes = brushes,
                ringGeometry =
                    RingGeometry(
                        color = glowColor,
                        strokePx = strokePx,
                        ring1Radius = iconRadiusPx + strokePx * 1.5f,
                        ring2Radius = iconRadiusPx + strokePx * 4.5f,
                    ),
            )
        }
    }
}

@Stable
class ChargeIconAssets(
    val glowBrushes: Map<ChargeLevel, Brush?>,
    val ringGeometry: RingGeometry,
)

data class RingGeometry(
    val color: Color,
    val strokePx: Float,
    val ring1Radius: Float,
    val ring2Radius: Float,
)

@Preview
@Composable
private fun ChargeIconPreview() {
    val assets = rememberChargeIconAssets()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(24.dp),
    ) {
        ChargeIcon(icon = MagicIcon.Sun, charge = ChargeLevel.Zero, assets = assets)
        ChargeIcon(icon = MagicIcon.Sun, charge = ChargeLevel.One, assets = assets)
        ChargeIcon(icon = MagicIcon.Sun, charge = ChargeLevel.Two, assets = assets)
    }
}

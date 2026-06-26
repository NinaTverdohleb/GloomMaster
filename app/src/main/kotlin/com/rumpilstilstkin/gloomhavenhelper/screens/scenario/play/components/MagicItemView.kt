package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.MagicIcon
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ChargeLevel

@Composable
fun ChargeIcon(
    icon: MagicIcon,
    charge: ChargeLevel,
    modifier: Modifier = Modifier,
    ringColor: Color = MaterialTheme.colorScheme.surfaceTint,
    glowColor: Color = ringColor,
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
            val iconRadius = iconSize.toPx() / 2f
            val strokePx = (iconRadius * 0.07f).coerceAtLeast(2f)

            // --- СВЕЧЕНИЕ ---
            val glowStrength = (energy / 2f).coerceIn(0f, 1f)
            if (glowStrength > 0.01f) {
                val glowRadius = iconRadius * (1.5f + 0.1f * glowStrength)
                val edgeStop = (iconRadius / glowRadius).coerceIn(0f, 1f)
                val peakStop = (edgeStop + (1f - edgeStop) * 0.25f).coerceIn(0f, 1f)
                val glowAlpha = 0.3f * glowStrength
                drawCircle(
                    brush =
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
                        ),
                    radius = glowRadius,
                    center = center,
                )
            }

            val ring1Alpha = energy.coerceIn(0f, 1f)
            if (ring1Alpha > 0.01f) {
                drawCircle(
                    color = ringColor.copy(alpha = ring1Alpha),
                    radius = iconRadius + strokePx * 1.5f,
                    center = center,
                    style = Stroke(width = strokePx),
                )
            }

            val ring2Alpha = (energy - 1f).coerceIn(0f, 1f)
            if (ring2Alpha > 0.01f) {
                drawCircle(
                    color = ringColor.copy(alpha = ring2Alpha),
                    radius = iconRadius + strokePx * 4.5f,
                    center = center,
                    style = Stroke(width = strokePx),
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

@Preview
@Composable
private fun ChargeIconPreview() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(24.dp),
    ) {
        ChargeIcon(icon = MagicIcon.Sun, charge = ChargeLevel.Zero)
        ChargeIcon(icon = MagicIcon.Sun, charge = ChargeLevel.One)
        ChargeIcon(icon = MagicIcon.Sun, charge = ChargeLevel.Two)
    }
}

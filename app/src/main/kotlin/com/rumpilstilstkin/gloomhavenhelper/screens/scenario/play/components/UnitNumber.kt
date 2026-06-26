package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.toNumberIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun UnitNumber(
    unitNumber: Int,
    isSpecial: Boolean,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
) {
    val tintColor =
        if (isSpecial) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.secondary
        }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        if (selected) {
            val glowColor: Color = MaterialTheme.colorScheme.primary
            val numberSize = 52.dp
            Canvas(modifier = Modifier.size(numberSize)) {
                val center = Offset(size.width / 2f, size.height / 2f)
                val iconRadius = numberSize.toPx() / 2f

                // --- СВЕЧЕНИЕ ---
                val glowStrength = 1f.coerceIn(0f, 1f)
                if (glowStrength > 0.01f) {
                    val glowRadius = iconRadius * (1.5f + 0.3f * glowStrength)
                    val glowAlpha = 0.4f * glowStrength

                    drawCircle(
                        brush =
                            Brush.radialGradient(
                                colorStops =
                                    arrayOf(
                                        0.0f to glowColor.copy(alpha = glowAlpha),
                                        0.5f to glowColor.copy(alpha = glowAlpha * 0.3f),
                                        1.0f to glowColor.copy(alpha = 0f),
                                    ),
                                center = center,
                                radius = glowRadius,
                            ),
                        radius = glowRadius,
                        center = center,
                    )
                }
            }
        }

        Icon(
            modifier = modifier.size(52.dp),
            painter = unitNumber.toNumberIcon().painter(),
            contentDescription = unitNumber.toString(),
            tint = tintColor,
        )
    }
}

@Preview
@Composable
private fun UnitNumberPreview() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            UnitNumber(
                unitNumber = 4,
                isSpecial = true,
            )

            UnitNumber(
                unitNumber = 12,
                isSpecial = false,
            )

            UnitNumber(
                unitNumber = 4,
                selected = true,
                isSpecial = true,
            )

            UnitNumber(
                unitNumber = 12,
                selected = true,
                isSpecial = false,
            )
        }
    }
}

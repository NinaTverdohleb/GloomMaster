package com.rumpilstilstkin.gloomhavenhelper.designsystem.components.counter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun GloomGloomCounterProgress(
    value: Int,
    intRange: IntRange,
    modifier: Modifier = Modifier,
) {
    val progress = (value.toFloat() / (intRange.last - intRange.first)).coerceIn(0f, 1f)

    LinearProgressIndicator(
        progress = { progress },
        modifier =
            modifier
                .fillMaxWidth()
                .height(12.dp)
                .clip(RoundedCornerShape(50))
                .background(MaterialTheme.colorScheme.surfaceContainerHighest),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceContainerHighest,
        strokeCap = StrokeCap.Round,
        gapSize = 0.dp,
        drawStopIndicator = {},
    )
}

@Composable
fun GloomCounterFull(
    value: Int,
    intRange: IntRange,
    modifier: Modifier = Modifier,
    showSign: Boolean = false,
    repeat: Boolean = false,
    onValueChange: (Int) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = CenterVertically,
    ) {
        GloomCountButton(
            value = value,
            type = PickerButtonType.MINUS,
            repeat = repeat,
            onValueChange = {
                onValueChange(checkRange(it, intRange))
            },
        )
        val text =
            if (showSign) {
                String.format(LocalLocale.current.platformLocale, "%+d", value)
            } else {
                value.toString()
            }
        Text(
            modifier =
                Modifier
                    .padding(horizontal = 12.dp),
            style = MaterialTheme.typography.headlineLarge,
            text = text,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        GloomCountButton(
            value = value,
            type = PickerButtonType.PLUS,
            repeat = repeat,
            onValueChange = {
                onValueChange(checkRange(it, intRange))
            },
        )
    }
}

@Composable
fun GloomCounterSmall(
    value: Int,
    intRange: IntRange,
    modifier: Modifier = Modifier,
    showSign: Boolean = false,
    onValueChange: (Int) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = CenterVertically,
    ) {
        GloomCountButtonSmall(
            value = value,
            type = PickerButtonType.MINUS,
            onValueChange = {
                onValueChange(checkRange(it, intRange))
            },
        )
        val text =
            if (showSign) {
                String.format(LocalLocale.current.platformLocale, "%+d", value)
            } else {
                value.toString()
            }
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            style = MaterialTheme.typography.titleLarge,
            text = text,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        GloomCountButtonSmall(
            value = value,
            type = PickerButtonType.PLUS,
            onValueChange = {
                onValueChange(checkRange(it, intRange))
            },
        )
    }
}

private fun checkRange(
    value: Int,
    range: IntRange,
): Int =
    when {
        value < range.first -> range.first
        value > range.last -> range.last
        else -> value
    }

@Preview
@Composable
private fun GloomCounterPreview() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            GloomCounterSmall(
                value = 5,
                intRange = IntRange(0, 15),
                onValueChange = {},
            )

            GloomCounterFull(
                value = 5,
                intRange = IntRange(0, 15),
                onValueChange = {},
            )

            GloomGloomCounterProgress(
                value = 5,
                intRange = IntRange(0, 15),
            )
        }
    }
}

package com.rumpilstilstkin.gloomhavenhelper.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun NumberPicker(
    value: Int,
    intRange: IntRange,
    modifier: Modifier = Modifier,
    showProgress: Boolean = false,
    progressColor: Color = MaterialTheme.colorScheme.primary,
    size: PickerSize = PickerSize.M,
    onValueChange: (Int) -> Unit,
) {
    val progress = (value.toFloat() / (intRange.last - intRange.first)).coerceIn(0f, 1f)
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(vertical = 4.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = CenterVertically
    ) {
        PickerButton(
            size = size,
            value = value,
            type = PickerButtonType.MINUS,
            onValueChange = {
                onValueChange(checkRange(it, intRange))
            }
        )
        val textStyle = when (size) {
            PickerSize.S -> MaterialTheme.typography.bodyLarge
            PickerSize.M -> MaterialTheme.typography.headlineMedium
        }
        val padding = when (size) {
            PickerSize.S -> 4.dp
            PickerSize.M -> 16.dp
        }
        Column(
            modifier = Modifier
                .align(CenterVertically)
                .weight(1f, fill = false)
                .defaultMinSize(minWidth = 48.dp)
                .padding(bottom = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(padding),
                style = textStyle,
                text = value.toString(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (showProgress) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .height(3.dp)
                        .fillMaxWidth()
                    ,
                    color = progressColor,
                    trackColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    strokeCap = StrokeCap.Round,
                    gapSize = 0.dp,
                    drawStopIndicator = {}
                )
            }
        }
        PickerButton(
            size = size,
            value = value,
            type = PickerButtonType.PLUS,
            onValueChange = {
                onValueChange(checkRange(it, intRange))
            }
        )

    }
}

private fun checkRange(value: Int, range: IntRange): Int {
    return when {
        value < range.first -> range.first
        value > range.last -> range.last
        else -> value
    }
}

@Preview
@Composable
private fun SampleM() {
    GloomhavenHalperTheme {
        NumberPicker(
            value = 5,
            intRange = IntRange(0, 15),
            onValueChange = {}
        )
    }

}

@Preview
@Composable
private fun SampleS() {
    GloomhavenHalperTheme {
        NumberPicker(
            size = PickerSize.S,
            value = 5,
            intRange = IntRange(0, 15),
            onValueChange = {}
        )
    }

}

@Preview
@Composable
private fun SampleLong() {
    GloomhavenHalperTheme {
        NumberPicker(
            modifier = Modifier.fillMaxWidth(),
            size = PickerSize.S,
            value = 16,
            intRange = IntRange(0, 16),
            onValueChange = {},
            showProgress = true,
            progressColor = Color.Red
        )
    }

}
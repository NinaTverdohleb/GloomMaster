package com.rumpilstilstkin.gloomhavenhelper.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme
import java.util.Locale

@Composable
fun NumberPicker(
    value: Int,
    intRange: IntRange,
    modifier: Modifier = Modifier,
    showSign: Boolean = false,
    size: GloomSize = GloomSize.M,
    numberBack: @Composable () -> Unit = {},
    onValueChange: (Int) -> Unit
) {
    Row(
        modifier = modifier,
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
            GloomSize.S -> MaterialTheme.typography.bodyLarge
            GloomSize.M -> MaterialTheme.typography.headlineMedium
        }
        val padding = when (size) {
            GloomSize.S -> 4.dp
            GloomSize.M -> 16.dp
        }
        Column(
            modifier = Modifier
                .align(CenterVertically)
                .weight(1f, fill = false)
                .defaultMinSize(minWidth = 48.dp)
                .padding(bottom = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val text = if (showSign) {
                String.format(Locale.getDefault(), "%+d", value)
            } else {
                value.toString()
            }
            Box {
                numberBack()
                Text(
                    modifier = Modifier
                        .padding(padding),
                    style = textStyle,
                    text = text,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
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


@Composable
fun NumberPickerProgress(
    value: Int,
    intRange: IntRange,
    modifier: Modifier = Modifier,
    showSign: Boolean = false,
    numberBack: @Composable () -> Unit = {},
    progressColor: Color = MaterialTheme.colorScheme.secondary,
    size: GloomSize = GloomSize.M,
    onValueChange: (Int) -> Unit,
) {
    val progress = (value.toFloat() / (intRange.last - intRange.first)).coerceIn(0f, 1f)
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
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
                GloomSize.S -> MaterialTheme.typography.bodyLarge
                GloomSize.M -> MaterialTheme.typography.headlineMedium
            }
            val padding = when (size) {
                GloomSize.S -> 4.dp
                GloomSize.M -> 16.dp
            }
            Column(
                modifier = Modifier
                    .align(CenterVertically)
                    .weight(1f, fill = false)
                    .defaultMinSize(minWidth = 48.dp)
                    .padding(bottom = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val text = if (showSign) {
                    String.format(Locale.getDefault(), "%+d", value)
                } else {
                    value.toString()
                }
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    numberBack()
                    Text(
                        modifier = Modifier
                            .padding(padding),
                        style = textStyle,
                        text = text,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
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
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .height(8.dp)
                .fillMaxWidth(),
            color = progressColor,
            trackColor = MaterialTheme.colorScheme.secondaryContainer,
            strokeCap = StrokeCap.Round,
            gapSize = 0.dp,
            drawStopIndicator = {}
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
private fun NumberPickerMPreview() {
    GloomhavenMasterTheme {
        NumberPicker(
            value = 5,
            intRange = IntRange(0, 15),
            onValueChange = {}
        )
    }

}

@Preview
@Composable
private fun NumberPickerSPreview() {
    GloomhavenMasterTheme {
        NumberPicker(
            size = GloomSize.S,
            value = 5,
            intRange = IntRange(0, 15),
            onValueChange = {}
        )
    }

}

@Preview
@Composable
private fun NumberPickerProgressPreview() {
    GloomhavenMasterTheme {
        NumberPickerProgress(
            modifier = Modifier.fillMaxWidth(),
            size = GloomSize.M,
            showSign = true,
            value = 5,
            intRange = IntRange(0, 16),
            onValueChange = {},
            numberBack = {
                Icon(
                    modifier = Modifier.size(64.dp),
                    painter = painterResource(id = R.drawable.ic_life),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }
        )
    }

}
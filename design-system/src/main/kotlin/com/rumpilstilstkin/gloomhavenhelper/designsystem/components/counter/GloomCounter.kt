package com.rumpilstilstkin.gloomhavenhelper.designsystem.components.counter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun GloomCounterFull(
    value: Int,
    intRange: IntRange,
    modifier: Modifier = Modifier,
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
            onValueChange = {
                onValueChange(checkRange(it, intRange))
            },
        )
        Column(
            modifier =
                Modifier
                    .align(CenterVertically)
                    .weight(1f, fill = false)
                    .defaultMinSize(minWidth = 48.dp)
                    .padding(bottom = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val text = value.toString()
            Box {
                Text(
                    modifier =
                        Modifier
                            .padding(12.dp),
                    style = MaterialTheme.typography.headlineLarge,
                    text = text,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        GloomCountButton(
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GloomCounterFull(
                value = 5,
                intRange = IntRange(0, 15),
                onValueChange = {},
            )

        }
    }
}
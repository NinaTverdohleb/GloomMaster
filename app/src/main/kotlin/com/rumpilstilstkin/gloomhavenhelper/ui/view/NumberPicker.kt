package com.rumpilstilstkin.gloomhavenhelper.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun NumberPicker(
    value: Int,
    intRange: IntRange,
    modifier: Modifier = Modifier,
    onValueChange: (Int) -> Unit,
) {
    Row(
        modifier = modifier.padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        PickerButton(
            value = value,
            type = PickerButtonType.MINUS,
            onValueChange = {
                onValueChange(checkRange(it, intRange))
            }
        )
        Text(
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 16.dp).align(CenterVertically),
            text = value.toString()
        )
        PickerButton(
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
private fun Sample() {
    GloomhavenHalperTheme {
        NumberPicker(
            value = 0,
            intRange = IntRange(0, 10),
            onValueChange = {}
        )
    }

}
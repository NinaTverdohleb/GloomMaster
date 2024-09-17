package com.rumpilstilstkin.gloomhavenhelper.ui.team

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.ui.view.NumberPicker

@Composable
fun NumberPickerDialog(
    startValue: Int,
    title: String,
    intRange: IntRange,
    onDismiss: () -> Unit,
    onNumberSelected: (Int) -> Unit
) {
    var value by remember { mutableIntStateOf(startValue) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            NumberPicker(
                modifier = Modifier.fillMaxWidth(),
                value = value,
                intRange = intRange
            ) {
                value = it
            }
        },
        confirmButton = {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onNumberSelected(value);
                }) {
                Text("Сохранить")
            }
        },
    )
}

@Preview
@Composable
private fun Sample() {
    NumberPickerDialog(
        startValue = 0,
        title = "Репутация",
        intRange = IntRange(0, 10),
        onDismiss = {},
        onNumberSelected = {}
    )
}
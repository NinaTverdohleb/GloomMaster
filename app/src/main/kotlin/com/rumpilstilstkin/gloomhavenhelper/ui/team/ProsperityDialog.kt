package com.rumpilstilstkin.gloomhavenhelper.ui.team

import androidx.compose.runtime.Composable

@Composable
fun ProsperityDialog(
    prosperity: Int,
    showDialog: Boolean,
    onSave: (Int) -> Unit,
    hideDialog: () -> Unit
) {
    if (showDialog) {
        NumberPickerDialog(
            startValue = prosperity,
            title = "Процветание",
            intRange = IntRange(0, 9),
            onDismiss = { hideDialog() },
            onNumberSelected = { onSave(it) }
        )
    }

}
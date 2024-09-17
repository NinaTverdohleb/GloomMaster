package com.rumpilstilstkin.gloomhavenhelper.ui.team

import androidx.compose.runtime.Composable
import com.rumpilstilstkin.gloomhavenhelper.screens.main.MainScreenAction

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
package com.rumpilstilstkin.gloomhavenhelper.ui.team

import androidx.compose.runtime.Composable

@Composable
fun ReputationDialog(
    reputation: Int,
    showDialog: Boolean,
    onSave: (Int) -> Unit,
    hideDialog: () -> Unit
) {
    if (showDialog) {
        NumberPickerDialog(
            startValue = reputation,
            title = "Репутация",
            intRange = IntRange(-20, 20),
            onDismiss = { hideDialog() },
            onNumberSelected = { onSave(it) }
        )
    }

}
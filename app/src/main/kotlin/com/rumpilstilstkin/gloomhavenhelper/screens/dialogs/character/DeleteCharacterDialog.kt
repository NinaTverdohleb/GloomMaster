package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.character

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun DeleteCharacterDialog(
    onDismiss: () -> Unit,
    delete: () -> Unit,
    retire: () -> Unit,
    canRetire: Boolean
) {

    GloomAlertDialog(
        title = "Удалить или увести на покой персонажа?",
        onDismissRequest = onDismiss,
        onConfirmRequest = null,
        onNeutralRequest = if (canRetire) retire else null,
        onNegativeRequest = delete,
        neutralText = "На покой",
        negativeText = "Удалить",
        content = {
            Text(
                text = "Это действие нельзя отменить.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    )
}

@Preview
@Composable
private fun DeleteCharacterDialogPreview() {
    GloomhavenHalperTheme {
        DeleteCharacterDialog(
            onDismiss = {},
            delete = {},
            retire = {},
            canRetire = true
        )
    }
}
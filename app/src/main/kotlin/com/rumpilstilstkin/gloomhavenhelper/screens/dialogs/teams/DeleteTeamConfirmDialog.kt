package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.teams

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@Composable
fun DeleteTeamDialog(
    teamName: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    GloomAlertDialog(
        title = "Удалить команду?",
        onDismissRequest = onDismiss,
        onConfirmRequest = null,
        onNeutralRequest = onDismiss,
        onNegativeRequest = onConfirm,
        negativeText = "Удалить",
        content = {
            Text(
                text = "Вы уверены, что хотите удалить команду \"$teamName\"? Это действие нельзя отменить.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    )
}

@Preview
@Composable
private fun DeleteTeamDialogPreview() {
    GloomhavenMasterTheme {
        DeleteTeamDialog(
            teamName = "Моя команда",
            onDismiss = {},
            onConfirm = {},
        )
    }
}
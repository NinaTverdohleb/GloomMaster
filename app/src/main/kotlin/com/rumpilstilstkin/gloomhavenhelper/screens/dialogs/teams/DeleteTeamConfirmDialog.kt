package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.teams

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@Composable
fun DeleteTeamDialog(
    teamName: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    GloomAlertDialog(
        title = stringResource(R.string.delete_team_confirm_title),
        onDismissRequest = onDismiss,
        onConfirmRequest = null,
        onNeutralRequest = onDismiss,
        onNegativeRequest = onConfirm,
        negativeText = stringResource(R.string.delete),
        content = {
            Text(
                text = stringResource(R.string.delete_team_confirm_warning, teamName),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
    )
}

@Preview
@Composable
private fun DeleteTeamDialogPreview() {
    GloomhavenMasterTheme {
        DeleteTeamDialog(
            teamName = "My Team",
            onDismiss = {},
            onConfirm = {},
        )
    }
}

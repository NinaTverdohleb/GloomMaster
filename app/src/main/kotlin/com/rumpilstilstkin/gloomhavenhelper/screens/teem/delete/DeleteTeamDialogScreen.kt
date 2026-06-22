package com.rumpilstilstkin.gloomhavenhelper.screens.teem.delete

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.ui.components.dialogs.ConfirmationDeleteDialog

@Composable
fun DeleteTeamDialogScreen(
    teamName: String,
    close: () -> Unit,
    deleteTeam: () -> Unit,
) {
    ConfirmationDeleteDialog(
        title = stringResource(R.string.delete_team_dialog_title),
        description = stringResource(R.string.delete_team_dialog_description, teamName),
        onClose = close,
        onDeleteConfirm = deleteTeam,
    )
}
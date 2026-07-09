package com.rumpilstilstkin.gloommaster.screens.characters.start.perks.delete

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.dialogs.ConfirmationDeleteDialog
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme

@Composable
fun DeletePerkDialog(
    delete: () -> Unit,
    close: () -> Unit,
) {
    ConfirmationDeleteDialog(
        title = stringResource(R.string.delete_perk_dialog_title),
        description = stringResource(R.string.delete_perk_dialog_description),
        onDeleteConfirm = delete,
        onClose = close,
    )
}

@Preview
@Composable
private fun DeletePerkDialogPreview() {
    GloomhavenMasterTheme {
        DeletePerkDialog(
            delete = {},
            close = {},
        )
    }
}

package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.monsters.unit.delete

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.dialogs.ConfirmationDeleteDialog
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun DeleteUnitDialog(
    monsterName: String,
    unitNumber: Int,
    delete: () -> Unit,
    close: () -> Unit,
) {
    ConfirmationDeleteDialog(
        title = stringResource(R.string.delete_unit_dialog_title),
        description = stringResource(R.string.delete_unit_dialog_description, monsterName, unitNumber),
        onDeleteConfirm = delete,
        onClose = close,
    )
}

@Preview
@Composable
private fun DeleteUnitDialogPreview() {
    GloomhavenMasterTheme {
        DeleteUnitDialog(
            monsterName = "Living Bones",
            unitNumber = 3,
            delete = {},
            close = {},
        )
    }
}

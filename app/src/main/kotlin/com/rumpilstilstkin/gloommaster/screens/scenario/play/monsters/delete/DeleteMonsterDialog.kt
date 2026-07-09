package com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters.delete

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.dialogs.ConfirmationDeleteDialog
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme

@Composable
fun DeleteMonsterDialog(
    monsterName: String,
    delete: () -> Unit,
    close: () -> Unit,
) {
    ConfirmationDeleteDialog(
        title = stringResource(R.string.delete_monster_dialog_title),
        description = stringResource(R.string.delete_monster_dialog_description, monsterName),
        onDeleteConfirm = delete,
        onClose = close,
    )
}

@Preview
@Composable
private fun DeleteMonsterDialogPreview() {
    GloomhavenMasterTheme {
        DeleteMonsterDialog(
            monsterName = "Living Bones",
            delete = {},
            close = {},
        )
    }
}

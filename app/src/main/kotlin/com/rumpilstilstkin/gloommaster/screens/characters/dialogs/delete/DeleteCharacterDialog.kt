package com.rumpilstilstkin.gloommaster.screens.characters.dialogs.delete

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.dialogs.ConfirmationDeleteDialog
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.screens.models.CharacterUI

@Composable
fun DeleteCharacterDialog(
    character: CharacterUI,
    delete: () -> Unit,
    close: () -> Unit,
) {
    ConfirmationDeleteDialog(
        title = stringResource(R.string.delete_character_title, character.name),
        description = stringResource(R.string.delete_warning),
        onDeleteConfirm = delete,
        onClose = close,
    )
}

@Preview
@Composable
private fun DeleteCharacterDialogPreview() {
    GloomhavenMasterTheme {
        DeleteCharacterDialog(
            character = CharacterUI.fixture(),
            delete = {},
            close = {},
        )
    }
}

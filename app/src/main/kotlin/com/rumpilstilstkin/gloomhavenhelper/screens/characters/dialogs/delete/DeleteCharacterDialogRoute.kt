package com.rumpilstilstkin.gloomhavenhelper.screens.characters.dialogs.delete

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloomhavenhelper.screens.core.OverlayContract
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI

object DeleteCharacterDialogContract : OverlayContract<CharacterUI, Unit> {

    @Composable
    override fun Content(input: CharacterUI, onDismissWithResult: (Unit?) -> Unit) {
        DeleteCharacterDialogRoute(
            character = input,
            close = onDismissWithResult,
        )
    }
}

@Composable
fun DeleteCharacterDialogRoute(
    character: CharacterUI,
    close: (Unit?) -> Unit,
    viewModel: DeleteCharacterDialogViewModel = hiltViewModel(),
) {
    val complete by viewModel.complete.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(complete) {
        complete?.let {
            close(Unit)
        }
    }

    DeleteCharacterDialog(
        character = character,
        close = { close(null) },
        delete = { viewModel.onAction(DeleteCharacterDialogAction.DeleteCharacter(character.id)) },
    )
}

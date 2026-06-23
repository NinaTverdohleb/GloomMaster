package com.rumpilstilstkin.gloomhavenhelper.screens.characters.dialogs.menu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloomhavenhelper.screens.core.OverlayContract
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI

object MenuCharacterDialogContract : OverlayContract<CharacterUI, MenuCharacterResult> {

    @Composable
    override fun Content(input: CharacterUI, onDismissWithResult: (MenuCharacterResult?) -> Unit) {
        MenuCharacterDialogRoute(
            character = input,
            close = onDismissWithResult,
        )
    }
}

@Composable
fun MenuCharacterDialogRoute(
    character: CharacterUI,
    close: (MenuCharacterResult) -> Unit,
    viewModel: MenuCharacterDialogViewModel = hiltViewModel(),
) {
    val complete by viewModel.complete.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(complete) {
        complete?.let { event ->
            close(event.result)
        }
    }

    MenuCharacterDialog(
        character = character,
        deleteCharacter = { viewModel.onAction(MenuCharacterDialogAction.DeleteCharacter(character)) },
        updateLevel = { viewModel.onAction(MenuCharacterDialogAction.UpdateLevel(character, it)) },
        leaveCharacter = { viewModel.onAction(MenuCharacterDialogAction.LeaveCharacter(character)) },
        makeCharacterAlive = { viewModel.onAction(MenuCharacterDialogAction.MakeCharacterAlive(character)) },
        openCharacterDetails = { viewModel.onAction(MenuCharacterDialogAction.OpenCharacterDetails(character)) },
    )
}

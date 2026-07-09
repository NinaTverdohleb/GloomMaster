package com.rumpilstilstkin.gloommaster.screens.characters.dialogs.character

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloommaster.screens.core.OverlayContract

object CharacterEditNameDialogContract : OverlayContract<Int, Unit> {
    @Composable
    override fun Content(
        input: Int,
        onDismissWithResult: (Unit?) -> Unit,
    ) {
        CharacterEditNameDialogRoute(
            characterId = input,
            close = onDismissWithResult,
        )
    }
}

@Composable
fun CharacterEditNameDialogRoute(
    characterId: Int,
    close: (Unit?) -> Unit,
) {
    val viewModel =
        hiltViewModel<CharacterEditNameDialogViewModel, CharacterEditNameDialogViewModel.Factory> { factory ->
            factory.create(characterId)
        }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val complete by viewModel.complete.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(complete) {
        complete?.let { close(Unit) }
    }

    CharacterEditNameDialog(
        currentName = uiState.name,
        save = { name ->
            viewModel.onAction(CharacterEditNameDialogAction.Save(name))
        },
    )
}

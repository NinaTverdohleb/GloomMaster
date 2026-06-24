package com.rumpilstilstkin.gloomhavenhelper.screens.characters.dialogs.add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloomhavenhelper.screens.core.OverlayContract

object AddCharacterDialogContract : OverlayContract<Unit, Unit> {
    @Composable
    override fun Content(
        input: Unit,
        onDismissWithResult: (Unit?) -> Unit,
    ) {
        AddCharacterDialogRoute(
            close = onDismissWithResult,
        )
    }
}

@Composable
fun AddCharacterDialogRoute(
    close: (Unit) -> Unit,
    viewModel: AddCharacterDialogViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val complete by viewModel.complete.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(complete) {
        complete?.let {
            close(Unit)
        }
    }

    AddCharacterDialog(
        state = state,
        createCharacter = { viewModel.onAction(AddCharacterDialogAction.AddCharacter) },
        updateName = { viewModel.onAction(AddCharacterDialogAction.UpdateName(it)) },
        updateLevel = { viewModel.onAction(AddCharacterDialogAction.UpdateLevel(it)) },
        changeType = { viewModel.onAction(AddCharacterDialogAction.SelectType(it)) },
    )
}

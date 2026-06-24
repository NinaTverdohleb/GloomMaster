package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloomhavenhelper.screens.core.OverlayContract

object EditTextDialogContract : OverlayContract<Int, Unit> {
    @Composable
    override fun Content(
        input: Int,
        onDismissWithResult: (Unit?) -> Unit,
    ) {
        EditTextDialogRoute(
            characterId = input,
        )
    }
}

@Composable
fun EditTextDialogRoute(characterId: Int) {
    val viewModel =
        hiltViewModel<EditTextDialogViewModel, EditTextDialogViewModel.Factory> { factory ->
            factory.create(characterId)
        }

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    EditTextDialog(
        text = state,
        onTextChanged = { viewModel.onAction(EditTextDialogAction.UpdateText(it)) },
    )
}

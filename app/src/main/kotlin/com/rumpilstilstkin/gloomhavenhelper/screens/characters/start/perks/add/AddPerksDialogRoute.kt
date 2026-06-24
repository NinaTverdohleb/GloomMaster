package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.perks.add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.dialogs.character.AddPerksDialog
import com.rumpilstilstkin.gloomhavenhelper.screens.core.OverlayContract

object AddPerksDialogContract : OverlayContract<AddPerksDialogInput, Unit> {
    @Composable
    override fun Content(
        input: AddPerksDialogInput,
        onDismissWithResult: (Unit?) -> Unit,
    ) {
        AddPerksDialogRoute(
            input = input,
            close = onDismissWithResult,
        )
    }
}

@Composable
fun AddPerksDialogRoute(
    input: AddPerksDialogInput,
    close: (Unit?) -> Unit,
    viewModel: AddPerksDialogViewModel = hiltViewModel(),
) {
    val complete by viewModel.complete.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(complete) {
        complete?.let {
            close(Unit)
        }
    }

    AddPerksDialog(
        avaliablePerks = input.availablePerks,
        onDismiss = { close(null) },
        onPerkSelected = { perksIds ->
            viewModel.onAction(
                AddPerksDialogAction.AddPerks(
                    perksIds = perksIds,
                    characterId = input.characterId,
                ),
            )
        },
    )
}

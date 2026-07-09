package com.rumpilstilstkin.gloommaster.screens.characters.start.perks.delete

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloommaster.screens.core.OverlayContract

object DeletePerkDialogContract : OverlayContract<DeletePerkDialogInput, Unit> {
    @Composable
    override fun Content(
        input: DeletePerkDialogInput,
        onDismissWithResult: (Unit?) -> Unit,
    ) {
        DeletePerkDialogRoute(
            input = input,
            close = onDismissWithResult,
        )
    }
}

@Composable
fun DeletePerkDialogRoute(
    input: DeletePerkDialogInput,
    close: (Unit?) -> Unit,
    viewModel: DeletePerkDialogViewModel = hiltViewModel(),
) {
    val complete by viewModel.complete.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(complete) {
        complete?.let {
            close(Unit)
        }
    }

    DeletePerkDialog(
        close = { close(null) },
        delete = {
            viewModel.onAction(
                DeletePerkDialogAction.DeletePerk(
                    perkId = input.perkId,
                    characterId = input.characterId,
                ),
            )
        },
    )
}

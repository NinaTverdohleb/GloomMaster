package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.goods.delete

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloomhavenhelper.screens.core.OverlayContract

object DeleteGoodDialogContract : OverlayContract<DeleteGoodDialogInput, Unit> {
    @Composable
    override fun Content(
        input: DeleteGoodDialogInput,
        onDismissWithResult: (Unit?) -> Unit,
    ) {
        DeleteGoodDialogRoute(
            input = input,
            close = onDismissWithResult,
        )
    }
}

@Composable
fun DeleteGoodDialogRoute(
    input: DeleteGoodDialogInput,
    close: (Unit?) -> Unit,
    viewModel: DeleteGoodDialogViewModel = hiltViewModel(),
) {
    val complete by viewModel.complete.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(complete) {
        complete?.let {
            close(Unit)
        }
    }

    DeleteGoodDialog(
        name = input.name,
        close = { close(null) },
        delete = {
            viewModel.onAction(
                DeleteGoodDialogAction.DeleteGood(
                    goodId = input.goodId,
                    characterId = input.characterId,
                ),
            )
        },
    )
}

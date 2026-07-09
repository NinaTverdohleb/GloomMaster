package com.rumpilstilstkin.gloommaster.screens.characters.start.goods.sell

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloommaster.screens.core.OverlayContract

object SellGoodDialogContract : OverlayContract<SellGoodDialogInput, Unit> {
    @Composable
    override fun Content(
        input: SellGoodDialogInput,
        onDismissWithResult: (Unit?) -> Unit,
    ) {
        SellGoodDialogRoute(
            input = input,
            close = onDismissWithResult,
        )
    }
}

@Composable
fun SellGoodDialogRoute(
    input: SellGoodDialogInput,
    close: (Unit?) -> Unit,
    viewModel: SellGoodDialogViewModel = hiltViewModel(),
) {
    val complete by viewModel.complete.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(complete) {
        complete?.let {
            close(Unit)
        }
    }

    SellGoodDialog(
        name = input.name,
        sellPrice = input.cost / 2,
        close = { close(null) },
        sell = {
            viewModel.onAction(
                SellGoodDialogAction.SellGood(
                    goodId = input.goodId,
                    characterId = input.characterId,
                    cost = input.cost,
                ),
            )
        },
    )
}

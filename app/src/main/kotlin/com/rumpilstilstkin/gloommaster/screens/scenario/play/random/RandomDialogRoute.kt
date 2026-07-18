package com.rumpilstilstkin.gloommaster.screens.scenario.play.random

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloommaster.screens.core.OverlayContract

object RandomDialogContract : OverlayContract<Unit, Unit> {
    @Composable
    override fun Content(
        input: Unit,
        onDismissWithResult: (Unit?) -> Unit,
    ) {
        RandomDialogRoute()
    }
}

@Composable
fun RandomDialogRoute(viewModel: RandomDialogViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RandomDialog(
        uiState = state,
        onCoin = { viewModel.onAction(RandomDialogAction.Coin) },
        onWeird = { viewModel.onAction(RandomDialogAction.Weird) },
    )
}

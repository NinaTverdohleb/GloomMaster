package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.complete

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloomhavenhelper.screens.core.OverlayContract

object CompleteScenarioDialogContract : OverlayContract<CompleteScenarioDialogInput, Unit> {
    @Composable
    override fun Content(
        input: CompleteScenarioDialogInput,
        onDismissWithResult: (Unit?) -> Unit,
    ) {
        CompleteScenarioDialogRoute(
            input = input,
            close = onDismissWithResult,
        )
    }
}

@Composable
fun CompleteScenarioDialogRoute(
    input: CompleteScenarioDialogInput,
    close: (Unit) -> Unit,
    viewModel: CompleteScenarioDialogViewModel = hiltViewModel(),
) {
    val complete by viewModel.complete.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(complete) {
        complete?.let {
            close(Unit)
        }
    }

    CompleteScenarioDialog(
        exp = input.exp,
        gold = input.gold,
        complete = { viewModel.onAction(CompleteScenarioDialogAction.Complete) },
    )
}

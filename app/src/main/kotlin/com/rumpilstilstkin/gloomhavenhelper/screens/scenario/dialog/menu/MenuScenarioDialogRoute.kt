package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.dialog.menu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloomhavenhelper.screens.core.OverlayContract
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI

object MenuScenarioDialogContract : OverlayContract<ShortScenarioUI, MenuScenarioResult> {

    @Composable
    override fun Content(input: ShortScenarioUI, onDismissWithResult: (MenuScenarioResult?) -> Unit) {
        MenuScenarioDialogRoute(
            scenario = input,
            close = onDismissWithResult
        )
    }
}

@Composable
fun MenuScenarioDialogRoute(
    scenario: ShortScenarioUI,
    close: (MenuScenarioResult) -> Unit,
    viewModel: MenuScenarioDialogViewModel = hiltViewModel(),
) {
    val complete by viewModel.complete.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(complete) {
        complete?.let { event ->
            close(event.result)
        }
    }

    MenuScenarioDialog(
        scenario = scenario,
        deleteScenario = {viewModel.onAction(MenuScenarioDialogAction.DeleteScenario(scenario))},
        restoreScenario = {viewModel.onAction(MenuScenarioDialogAction.RestoreScenario(scenario))},
        competeScenario = {viewModel.onAction(MenuScenarioDialogAction.CompleteScenario(scenario))},
        playScenario = {viewModel.onAction(MenuScenarioDialogAction.StartScenario(scenario))},
    )
}

package com.rumpilstilstkin.gloommaster.screens.scenario.dialog.delete

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloommaster.screens.core.OverlayContract
import com.rumpilstilstkin.gloommaster.screens.models.ShortScenarioUI

object DeleteScenarioDialogContract : OverlayContract<ShortScenarioUI, Unit> {
    @Composable
    override fun Content(
        input: ShortScenarioUI,
        onDismissWithResult: (Unit?) -> Unit,
    ) {
        DeleteScenarioDialogRoute(
            scenario = input,
            close = onDismissWithResult,
        )
    }
}

@Composable
fun DeleteScenarioDialogRoute(
    scenario: ShortScenarioUI,
    close: (Unit?) -> Unit,
    viewModel: DeleteScenarioDialogViewModel = hiltViewModel(),
) {
    val complete by viewModel.complete.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(complete) {
        complete?.let {
            close(Unit)
        }
    }

    DeleteScenarioDialogScreen(
        scenario = scenario,
        close = { close(null) },
        deleteScenario = { viewModel.onAction(DeleteScenarioDialogAction.DeleteScenario(scenario.scenarioNumber)) },
    )
}

package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.dialog.add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloomhavenhelper.screens.core.OverlayContract
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI

object AddScenarioDialogContract : OverlayContract<ShortScenarioUI, Unit> {
    @Composable
    override fun Content(
        input: ShortScenarioUI,
        onDismissWithResult: (Unit?) -> Unit,
    ) {
        AddScenarioDialogRoute(
            scenario = input,
            close = onDismissWithResult,
        )
    }
}

@Composable
fun AddScenarioDialogRoute(
    scenario: ShortScenarioUI,
    close: (Unit) -> Unit,
    viewModel: AddScenarioDialogViewModel = hiltViewModel(),
) {
    val complete by viewModel.complete.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(complete) {
        complete?.let {
            close(Unit)
        }
    }

    AddScenarioDialog(
        scenario = scenario,
        addScenario = { viewModel.onAction(AddScenarioDialogAction.AddScenario(scenario)) },
    )
}

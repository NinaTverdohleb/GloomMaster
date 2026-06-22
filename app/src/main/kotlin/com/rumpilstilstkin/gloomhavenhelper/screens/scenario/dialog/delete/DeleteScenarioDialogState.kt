package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.dialog.delete

sealed interface DeleteScenarioDialogAction {
    data class DeleteScenario(val scenarioNumber: Int) : DeleteScenarioDialogAction
}

data object DeleteScenarioDialogComplete
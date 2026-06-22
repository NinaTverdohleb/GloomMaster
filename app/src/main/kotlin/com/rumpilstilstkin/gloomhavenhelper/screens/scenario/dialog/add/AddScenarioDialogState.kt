package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.dialog.add

import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI

sealed interface AddScenarioDialogAction {
    data class AddScenario(val scenario: ShortScenarioUI) : AddScenarioDialogAction
}

data object AddScenarioDialogComplete

package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.dialog.menu

import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI

sealed interface MenuScenarioDialogAction {
    data class StartScenario(
        val scenario: ShortScenarioUI,
    ) : MenuScenarioDialogAction

    data class CompleteScenario(
        val scenario: ShortScenarioUI,
    ) : MenuScenarioDialogAction

    data class DeleteScenario(
        val scenario: ShortScenarioUI,
    ) : MenuScenarioDialogAction

    data class RestoreScenario(
        val scenario: ShortScenarioUI,
    ) : MenuScenarioDialogAction
}

data class MenuScenarioDialogComplete(
    val result: MenuScenarioResult,
)

sealed interface MenuScenarioResult {
    data class ScenarioRestored(
        val scenario: ShortScenarioUI,
    ) : MenuScenarioResult

    data class ScenarioCompleted(
        val scenario: ShortScenarioUI,
    ) : MenuScenarioResult

    data class PlayScenario(
        val scenario: ShortScenarioUI,
    ) : MenuScenarioResult

    data class DeleteScenarioRequest(
        val scenario: ShortScenarioUI,
    ) : MenuScenarioResult
}

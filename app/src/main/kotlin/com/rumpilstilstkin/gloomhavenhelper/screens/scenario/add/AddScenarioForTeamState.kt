package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.add

import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class AddScenarioForTeamUiState(
    val scenarios: ImmutableList<ShortScenarioUI> = persistentListOf(),
    val searchText: String = "",
)

data class AddScenarioForTeamLogicState(
    val searchText: String = "",
)

sealed interface AddScenarioForTeamAction {
    data class SearchTextChange(
        val text: String,
    ) : AddScenarioForTeamAction

    data class SelectScenario(
        val scenario: ShortScenarioUI,
    ) : AddScenarioForTeamAction

    data object Back : AddScenarioForTeamAction
}

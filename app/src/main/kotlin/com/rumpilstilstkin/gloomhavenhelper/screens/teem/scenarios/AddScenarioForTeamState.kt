package com.rumpilstilstkin.gloomhavenhelper.screens.teem.scenarios

import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class AddScenarioForTeamUiState(
    val scenarios: ImmutableList<ShortScenarioUI> = persistentListOf(),
    val searchText: String = "",
    val selectedScenario: ShortScenarioUI? = null,
)

data class AddScenarioForTeamLogicState(
    val searchText: String = "",
    val selectedScenario: ShortScenarioUI? = null,
)

sealed interface AddScenarioForTeamAction {
    data class SearchTextChange(
        val text: String,
    ) : AddScenarioForTeamAction

    data class SelectScenario(
        val scenario: ShortScenarioUI,
    ) : AddScenarioForTeamAction

    data object DismissDialog : AddScenarioForTeamAction

    data object ConfirmAddScenario : AddScenarioForTeamAction

    data object Back : AddScenarioForTeamAction
}

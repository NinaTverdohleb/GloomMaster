package com.rumpilstilstkin.gloomhavenhelper.screens.start.team

import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.TeamUI

sealed interface TeamTabUiState {
    data object Empty : TeamTabUiState

    data class Data(
        val currentTeam: TeamUI,
    ) : TeamTabUiState {
        companion object {
            fun fixture(team: TeamUI = TeamUI.fixture()) =
                Data(currentTeam = team)
        }
    }
}

sealed interface TeamTabAction {
    data class UpdateReputation(
        val value: Int,
    ) : TeamTabAction

    data class UpdateProsperity(
        val value: Int,
    ) : TeamTabAction

    data object OpenTeamAchievements : TeamTabAction

    data object OpenGlobalAchievements : TeamTabAction

    data object RestoreLastScenario : TeamTabAction

    data object Donate : TeamTabAction

    data class SelectScenario(
        val scenario: ShortScenarioUI
    ) : TeamTabAction
}


package com.rumpilstilstkin.gloomhavenhelper.screens.start.team

import com.rumpilstilstkin.gloomhavenhelper.screens.models.TeamUI

sealed interface TeamTabUiState {
    data object Empty : TeamTabUiState
    data class Data(
        val currentTeam: TeamUI,
    ) : TeamTabUiState {
        companion object {
            fun fixture(
                team: TeamUI = TeamUI.fixture()
            ) = Data(
                currentTeam = team,
            )
        }
    }
}
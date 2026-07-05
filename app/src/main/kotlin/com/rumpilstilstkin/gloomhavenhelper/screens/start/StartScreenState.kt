package com.rumpilstilstkin.gloomhavenhelper.screens.start

import androidx.compose.runtime.Immutable

sealed interface StartScreenState {
    // Initial value while the current team is loading; distinct from Empty so
    // ReportDrawnWhen (TTFD) fires only on a real state, not the placeholder.
    data object Loading : StartScreenState

    data object Empty : StartScreenState

    @Immutable
    data class Team(
        val id: Int,
        val name: String,
    ) : StartScreenState
}

sealed interface StartScreenAction {
    data object AddTeam : StartScreenAction

    data object Settings : StartScreenAction
}

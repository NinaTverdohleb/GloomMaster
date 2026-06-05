package com.rumpilstilstkin.gloomhavenhelper.screens.start

import androidx.compose.runtime.Immutable

sealed interface StartScreenState {
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

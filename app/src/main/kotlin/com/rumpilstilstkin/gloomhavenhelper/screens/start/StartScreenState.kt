package com.rumpilstilstkin.gloomhavenhelper.screens.start

sealed interface StartScreenState {
    data object Empty : StartScreenState
    data class Team(
        val id: Int,
        val name: String,
        val showSelectTeamDialog: Boolean = false
    ) : StartScreenState
}

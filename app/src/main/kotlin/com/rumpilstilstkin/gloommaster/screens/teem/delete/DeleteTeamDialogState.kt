package com.rumpilstilstkin.gloommaster.screens.teem.delete

sealed interface DeleteTeamDialogAction {
    data class DeleteTeam(
        val teamId: Int,
    ) : DeleteTeamDialogAction
}

data object DeleteTeamDialogComplete

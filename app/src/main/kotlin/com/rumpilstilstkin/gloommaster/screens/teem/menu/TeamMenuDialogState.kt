package com.rumpilstilstkin.gloommaster.screens.teem.menu

import com.rumpilstilstkin.gloommaster.screens.models.ShortTeamInfoUi

sealed interface TeamMenuDialogAction {
    data class SelectTeam(
        val team: ShortTeamInfoUi,
    ) : TeamMenuDialogAction

    data class DeleteTeam(
        val team: ShortTeamInfoUi,
    ) : TeamMenuDialogAction
}

data class TeamMenuDialogComplete(
    val result: TeamMenuResult,
)

sealed interface TeamMenuResult {
    data object NewTeamSelected : TeamMenuResult

    data class DeleteTeamRequest(
        val team: ShortTeamInfoUi,
    ) : TeamMenuResult
}

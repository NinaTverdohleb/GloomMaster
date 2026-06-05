package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.teams.change

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortTeamInfoUi
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class TeamListDialogState(
    val teams: ImmutableList<ShortTeamInfoUi>,
)

sealed interface TeamListDialogAction {
    data class SelectTeam(
        val teamId: Int,
    ) : TeamListDialogAction

    data object Back : TeamListDialogAction
}

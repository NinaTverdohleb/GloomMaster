package com.rumpilstilstkin.gloommaster.screens.teem.list

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloommaster.screens.models.ShortTeamInfoUi
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class TeamListDialogState(
    val teams: ImmutableList<ShortTeamInfoUi>,
)

sealed interface TeamListDialogAction {
    data class SelectTeam(
        val team: ShortTeamInfoUi,
    ) : TeamListDialogAction
}

data class TeamListDialogComplete(
    val team: ShortTeamInfoUi,
)

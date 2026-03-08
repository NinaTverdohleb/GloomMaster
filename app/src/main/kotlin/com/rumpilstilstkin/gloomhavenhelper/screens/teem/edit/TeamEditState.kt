package com.rumpilstilstkin.gloomhavenhelper.screens.teem.edit

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortTeamInfoUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class TeamEditStateUi(
    val teamName: String = "",
    val availablePacks: ImmutableList<PackItemUi> = persistentListOf(),
    val showDeleteConfirmDialog: Boolean = false,
    val showTeamListDialog: Boolean = false,
    val showChangeTeamButton: Boolean = false,
    val teamsForSelect: ImmutableList<ShortTeamInfoUi> = persistentListOf(),
)

data class TeamEditStateLogic(
    val showDeleteConfirmDialog: Boolean = false,
    val showTeamListDialog: Boolean = false,
)

@Immutable
data class PackItemUi(
    val pack: PackType,
    val isEnabled: Boolean,
) {
    val displayName: String = when (pack) {
        PackType.MAIN -> "Gloomhaven"
        PackType.FORGOTTEN_CIRCLES -> "Забытые круги"
    }
}

sealed interface TeamEditAction {
    data class ChangeTeamName(val name: String) : TeamEditAction
    data class TogglePack(val pack: PackType) : TeamEditAction
    data object Back : TeamEditAction
    data object ShowDeleteConfirmDialog : TeamEditAction
    data object DismissDeleteConfirmDialog : TeamEditAction
    data object ConfirmDelete : TeamEditAction
    data object ShowTeamListDialog : TeamEditAction
    data object DismissTeamListDialog : TeamEditAction
    data class SelectTeam(val teamId: Int) : TeamEditAction
}

package com.rumpilstilstkin.gloomhavenhelper.screens.teem.edit

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.DifficultyLevel
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortTeamInfoUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class TeamEditStateUi(
    val teamName: String = "",
    val availablePacks: ImmutableList<PackItemUi> = persistentListOf(),
    val difficultyLevel: DifficultyLevel = DifficultyLevel.NORMAL,
    val showDeleteConfirmDialog: Boolean = false,
    val showTeamListDialog: Boolean = false,
    val showChangeTeamButton: Boolean = false,
    val teamsForSelect: ImmutableList<ShortTeamInfoUi> = persistentListOf(),
    val createShareFileInProgress: Boolean = false,
)

data class TeamEditStateLogic(
    val showDeleteConfirmDialog: Boolean = false,
    val showTeamListDialog: Boolean = false,
    val createShareFileInProgress: Boolean = false,
)

@Immutable
data class PackItemUi(
    val pack: PackType,
    val isEnabled: Boolean,
) {
    @get:StringRes
    val displayNameRes: Int = when (pack) {
        PackType.MAIN -> R.string.pack_main
        PackType.FORGOTTEN_CIRCLES -> R.string.pack_forgotten_circles
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
    data object ShareTeam : TeamEditAction
    data class ChangeDifficultyLevel(val level: DifficultyLevel): TeamEditAction
}

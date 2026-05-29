package com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Achievement
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AchievementWithName
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class AchievementsStateUi(
    val achievements: ImmutableList<AchievementWithName> = persistentListOf(),
    val availableAchievements: ImmutableList<AchievementWithName> = persistentListOf(),
    val showAddDialog: Boolean = false,
    val achievementToDelete: String? = null,
)

data class AchievementsStateLogic(
    val showAddDialog: Boolean = false,
    val achievementToDelete: String? = null,
)

sealed interface AchievementsAction {
    data object ShowAddDialog : AchievementsAction
    data object DismissAddDialog : AchievementsAction
    data class AddAchievement(val achievement: AchievementWithName) : AchievementsAction
    data class ShowDeleteConfirmDialog(val achievementSlug: String) : AchievementsAction
    data object DismissDeleteConfirmDialog : AchievementsAction
    data object ConfirmDelete : AchievementsAction
    data object Back : AchievementsAction
    data class UpdateAchievement(val value: Int, val achievement: AchievementWithName) : AchievementsAction
}

package com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AchievementWithName
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class AchievementsStateUi(
    val achievements: ImmutableList<AchievementWithName> = persistentListOf(),
    val availableAchievements: ImmutableList<AchievementWithName> = persistentListOf(),
)

sealed interface AchievementsAction {
    data object ShowAddDialog : AchievementsAction

    data class DeleteAchievement(
        val achievement: AchievementWithName,
    ) : AchievementsAction

    data object Back : AchievementsAction

    data class UpdateAchievement(
        val value: Int,
        val achievement: AchievementWithName,
    ) : AchievementsAction
}

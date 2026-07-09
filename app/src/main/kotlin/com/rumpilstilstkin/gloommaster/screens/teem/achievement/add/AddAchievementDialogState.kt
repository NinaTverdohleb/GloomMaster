package com.rumpilstilstkin.gloommaster.screens.teem.achievement.add

import com.rumpilstilstkin.gloommaster.domain.entity.AchievementWithName

sealed interface AddAchievementDialogAction {
    data class AddAchievement(
        val achievement: AchievementWithName,
    ) : AddAchievementDialogAction
}

data object AddAchievementDialogComplete

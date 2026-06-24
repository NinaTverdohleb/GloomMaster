package com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.add

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AchievementWithName

sealed interface AddAchievementDialogAction {
    data class AddAchievement(
        val achievement: AchievementWithName,
    ) : AddAchievementDialogAction
}

data object AddAchievementDialogComplete

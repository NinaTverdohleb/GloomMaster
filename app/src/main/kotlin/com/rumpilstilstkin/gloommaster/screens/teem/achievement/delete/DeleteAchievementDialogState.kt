package com.rumpilstilstkin.gloommaster.screens.teem.achievement.delete

sealed interface DeleteAchievementDialogAction {
    data class DeleteAchievement(
        val achievementSlug: String,
    ) : DeleteAchievementDialogAction
}

data object DeleteAchievementDialogComplete

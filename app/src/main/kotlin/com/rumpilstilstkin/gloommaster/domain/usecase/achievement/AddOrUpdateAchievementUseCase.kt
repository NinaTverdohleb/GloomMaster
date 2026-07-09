package com.rumpilstilstkin.gloommaster.domain.usecase.achievement

import com.rumpilstilstkin.gloommaster.data.TeamRepository
import com.rumpilstilstkin.gloommaster.domain.entity.Achievement
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AddOrUpdateAchievementUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
) {
    suspend operator fun invoke(achievement: Achievement) {
        val team = teamRepository.currentTeam.first() ?: return
        val updatedAchievements =
            team.achievements.filter { it.slug != achievement.slug } + achievement
        teamRepository.updateTeam(team.copy(achievements = updatedAchievements))
    }
}

package com.rumpilstilstkin.gloommaster.domain.usecase.achievement

import com.rumpilstilstkin.gloommaster.data.TeamRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RemoveAchievementUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
) {
    suspend operator fun invoke(achievementSlug: String) {
        val team = teamRepository.currentTeam.first() ?: return
        val updatedAchievements = team.achievements.filter { it.slug != achievementSlug }
        teamRepository.updateTeam(team.copy(achievements = updatedAchievements))
    }
}

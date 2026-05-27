package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.achievement

import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Achievement
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RemoveGlobalAchievementUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
) {
    suspend operator fun invoke(achievement: Achievement) {
        val team = teamRepository.currentTeam.first() ?: return
        val updatedAchievements = team.globalAchievement.filter { it.key != achievement.key }
        teamRepository.updateTeam(team.copy(globalAchievement = updatedAchievements))
    }
}

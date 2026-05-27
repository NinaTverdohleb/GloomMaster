package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.achievement

import com.rumpilstilstkin.gloomhavenhelper.data.AchievementRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Achievement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAvailableTeamAchievementsUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val achievementRepository: AchievementRepository,
) {
    operator fun invoke(): Flow<List<Achievement>> =
        teamRepository.currentTeam.map { team ->
            if (team == null) {
                emptyList()
            } else {
                val allAchievements = achievementRepository.getTeamAchievementsByPacks(team.packs.map { it.name })
                val existingKeys = team.teamAchievement.map { it.key }.toSet()
                allAchievements.filter { it.key !in existingKeys }
            }
        }
}

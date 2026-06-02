package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.achievement

import com.rumpilstilstkin.gloomhavenhelper.data.AchievementRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AchievementWithName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetAvailableTeamAchievementsUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val achievementRepository: AchievementRepository,
) {
    operator fun invoke(): Flow<List<AchievementWithName>> =
        teamRepository.currentTeam.combine(achievementRepository.dictionary) { team, dictionary ->
            if (team == null) {
                emptyList()
            } else {
                val allAchievements =
                    achievementRepository.getTeamAchievementsByPacks(team.packs.map { it.name })
                val existingNames = team.achievements.map { it.slug }.toSet()
                allAchievements
                    .filter { it.slug !in existingNames }
                    .map { achievement ->
                        AchievementWithName(
                            slug = achievement.slug,
                            name = dictionary[achievement.slug] ?: "",
                            value = achievement.value,
                            maxValue = achievement.maxValue,
                            isGlobal = achievement.isGlobal,
                        )
                    }
            }
        }
}

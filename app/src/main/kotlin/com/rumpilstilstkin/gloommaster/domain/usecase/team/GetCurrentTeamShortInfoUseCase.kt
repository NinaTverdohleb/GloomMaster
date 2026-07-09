package com.rumpilstilstkin.gloommaster.domain.usecase.team

import com.rumpilstilstkin.gloommaster.data.AchievementRepository
import com.rumpilstilstkin.gloommaster.data.TeamRepository
import com.rumpilstilstkin.gloommaster.domain.entity.AchievementWithName
import com.rumpilstilstkin.gloommaster.domain.entity.ShortTeamInfoWithTranslations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCurrentTeamShortInfoUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val achievementRepository: AchievementRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<ShortTeamInfoWithTranslations?> =
        teamRepository.currentTeam.flatMapLatest { teamInfo ->
            achievementRepository.dictionary.map { dictionary ->
                teamInfo?.let {
                    ShortTeamInfoWithTranslations(
                        teamId = teamInfo.teamId,
                        name = teamInfo.name,
                        achievements =
                            teamInfo.achievements.map { achievement ->
                                AchievementWithName(
                                    slug = achievement.slug,
                                    value = achievement.value,
                                    maxValue = achievement.maxValue,
                                    name = dictionary[achievement.slug] ?: "",
                                    isGlobal = achievement.isGlobal,
                                )
                            },
                        reputation = teamInfo.reputation,
                        prosperity = teamInfo.prosperity,
                        packs = teamInfo.packs,
                        aliveCharacterIds = teamInfo.aliveCharacterIds,
                        churchValue = teamInfo.churchValue,
                        difficultyLevel = teamInfo.difficultyLevel,
                    )
                }
            }
        }
}

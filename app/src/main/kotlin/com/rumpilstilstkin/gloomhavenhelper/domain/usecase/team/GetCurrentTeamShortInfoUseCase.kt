package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

import com.rumpilstilstkin.gloomhavenhelper.data.AchievementRepository
import com.rumpilstilstkin.gloomhavenhelper.data.LocaleRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AchievementWithName
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.DifficultyLevel
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfoWithTranslations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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

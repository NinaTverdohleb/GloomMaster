package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.toLevel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

class GetCurrentTeamUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val characterRepository: CharacterRepository,
    private val getDiscountByReputation: GetDiscountByReputationUseCase,
    private val getTeamProsperityUseCase: GetTeamProsperityUseCase
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<TeamInfo> =
        teamRepository.currentTeamId.flatMapConcat { teamId ->
            characterRepository.getCharacterByTeamId(teamId)
                .combine(teamRepository.getTeamWithScenarioFlow(teamId)) { characters, team ->
                    val activeCharacters = characters.filter { it.isAlive }
                    val activeScenarios = team.scenario.filter { !it.isCompleted }
                    TeamInfo(
                        id = team.teamId,
                        name = team.name,
                        level = activeCharacters.map { it.level }.toLevel(),
                        teamAchievement = team.teamAchievement,
                        globalAchievement = team.globalAchievement,
                        reputation = team.reputation,
                        prosperity = getTeamProsperityUseCase(team.prosperity),
                        scenario = activeScenarios,
                        characters = activeCharacters,
                        shopDiscount = getDiscountByReputation(team.reputation)
                    )
                }
        }
}
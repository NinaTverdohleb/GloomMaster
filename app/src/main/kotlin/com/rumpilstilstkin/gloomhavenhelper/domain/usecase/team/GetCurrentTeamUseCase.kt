package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioGameStateRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.toLevel
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.FilterTeamScenariosUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetCurrentTeamUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val characterRepository: CharacterRepository,
    private val getDiscountByReputation: GetDiscountByReputationUseCase,
    private val getTeamProsperityUseCase: GetTeamProsperityUseCase,
    private val filterTeamScenariosUseCase: FilterTeamScenariosUseCase,
    private val scenarioGameStateRepository: ScenarioGameStateRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<TeamInfo?> =
        teamRepository.currentTeam.flatMapLatest { currentTeam ->
            currentTeam?.let { team ->
                combine(
                    characterRepository.getCharacterByTeamId(team.teamId),
                    teamRepository.getTeamWithScenarioFlow(team.teamId),
                    scenarioGameStateRepository.getFlow()
                ) { characters, team, activeScenario ->
                    val activeCharacters = characters.filter { it.isAlive }
                    val teamScenarios = filterTeamScenariosUseCase(team)
                    TeamInfo(
                        id = team.teamId,
                        name = team.name,
                        level = activeCharacters.map { it.level }.toLevel(team.difficultyLevel),
                        teamAchievement = team.teamAchievement,
                        globalAchievement = team.globalAchievement,
                        reputation = team.reputation,
                        prosperity = getTeamProsperityUseCase(team.prosperity),
                        activeScenario = teamScenarios.activeScenarios,
                        aliveCharacters = activeCharacters,
                        shopDiscount = getDiscountByReputation(team.reputation),
                        packs = team.packs,
                        hasActiveScenario = activeScenario != null,
                        churchValue = team.churchValue,
                        difficultyLevel = team.difficultyLevel
                    )
                }
            } ?: flowOf(null)
        }
}
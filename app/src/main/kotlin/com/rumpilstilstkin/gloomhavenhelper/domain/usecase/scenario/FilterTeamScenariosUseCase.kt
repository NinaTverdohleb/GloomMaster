package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfoWithScenario
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamScenarios
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class FilterTeamScenariosUseCase @Inject constructor() {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(team: TeamInfoWithScenario): TeamScenarios {
        val allAchievement = (team.teamAchievement + team.globalAchievement)
            .map { it.key }
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .toSet()
        val completed = team.scenario.filter { it.isCompleted }
        val avaliable = team.scenario
            .filter { !it.isCompleted }.toSet()
        val (active, blocked) = avaliable.partition { scenario ->
            scenario.scenarioRequirements.evaluate(allAchievement)
        }
        return TeamScenarios(
            activeScenarios = active,
            blockedScenarios = blocked,
            completedScenarios = completed,
        )
    }
}
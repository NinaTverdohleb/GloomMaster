package com.rumpilstilstkin.gloomhavenhelper.domain.usecase

import android.util.Log
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class CompleteScenarioUsecase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val scenarioRepository: ScenarioRepository
) {
    suspend operator fun invoke(scenarioNumber: Int) {
        Log.d("Dto", "scenarioNumber: $scenarioNumber")
        teamRepository.currentTeamId.first().let { teamId ->
            Log.d("Dto", "teamId: $teamId")

            teamRepository.getTeam(teamId).let { team ->
                Log.d("Dto", "teamName: ${team.name}")

                val oldScenarios = scenarioRepository.getAllTeamScenarios(teamId)
                scenarioRepository.getScenario(scenarioNumber).let { scenario ->
                    Log.d("Dto", "scenarioName: ${scenario.scenarioName}")

                    val newScenario =
                        scenario.newScenario.map { scenarioRepository.getScenario(it) }.filter {
                            oldScenarios.none { oldScenario -> oldScenario.scenarioNumber == it.scenarioNumber }
                        }
                    scenarioRepository.completeTeamScenario(teamId, scenarioNumber)
                    newScenario.forEach {
                        scenarioRepository.saveTeamScenario(it, teamId)
                    }
                    val newTeamAchievements = (team.teamAchievement + scenario.teamAchievements).distinct()
                    val newGlobalAchievements = (team.globalAchievement + scenario.globalAchievements).distinct()
                    teamRepository.updateTeam(
                        team.copy(
                            teamAchievement = newTeamAchievements,
                            globalAchievement = newGlobalAchievements
                        )
                    )
                }
            }
        }

    }
}
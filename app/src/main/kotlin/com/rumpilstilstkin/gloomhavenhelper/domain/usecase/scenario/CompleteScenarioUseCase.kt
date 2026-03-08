package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

import android.util.Log
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

// TODO check logic
class CompleteScenarioUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val scenarioRepository: ScenarioRepository
) {
    suspend operator fun invoke(scenarioNumber: Int) {
        teamRepository.currentTeam.first()?.let { team ->
                Log.d("Dto", "teamName: ${team.name}")

                val oldScenarios = scenarioRepository.getAllTeamScenarios(team.teamId)
                scenarioRepository.getScenario(scenarioNumber).let { scenario ->
                    Log.d("Dto", "scenarioName: ${scenario.scenarioName}")

                    val newScenario =
                        scenario.newScenario.map { scenarioRepository.getScenario(it) }.filter { newScenario ->
                            oldScenarios.none { oldScenario -> oldScenario.scenarioNumber == newScenario.scenarioNumber }
                                    && newScenario.pack in team.packs.toSet()
                        }
                    scenarioRepository.completeTeamScenario(team.teamId, scenarioNumber)
                    newScenario.forEach {
                        scenarioRepository.saveTeamScenario(it, team.teamId)
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
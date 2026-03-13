package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioGameStateRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CompleteScenarioUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val scenarioRepository: ScenarioRepository,
    private val scenarioGameStateRepository: ScenarioGameStateRepository
) {
    suspend operator fun invoke(scenarioNumber: Int) {
        teamRepository.currentTeam.first()?.let { team ->
            val oldScenarios = scenarioRepository.getAllTeamScenarios(team.teamId)
            scenarioRepository.getScenario(scenarioNumber).let { scenario ->
                val newScenario =
                    scenario.newScenario.map { scenarioRepository.getScenario(it) }
                        .filter { newScenario ->
                            oldScenarios.none { oldScenario -> oldScenario.scenarioNumber == newScenario.scenarioNumber }
                                    && newScenario.pack in team.packs.toSet()
                        }
                scenarioRepository.completeTeamScenario(team.teamId, scenarioNumber)
                scenarioGameStateRepository.delete()
                newScenario.forEach {
                    scenarioRepository.saveTeamScenario(it, team.teamId)
                }
            }
        }
    }
}
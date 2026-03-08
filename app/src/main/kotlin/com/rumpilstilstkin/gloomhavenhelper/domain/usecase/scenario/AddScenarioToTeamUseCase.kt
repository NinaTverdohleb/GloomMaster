package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AddScenarioToTeamUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val scenarioRepository: ScenarioRepository,
) {
    suspend operator fun invoke(scenarioNumber: Int) {
        val team = teamRepository.currentTeam.first() ?: return
        val scenario = scenarioRepository.getScenario(scenarioNumber)
        scenarioRepository.saveTeamScenario(scenario, team.teamId)
    }
}

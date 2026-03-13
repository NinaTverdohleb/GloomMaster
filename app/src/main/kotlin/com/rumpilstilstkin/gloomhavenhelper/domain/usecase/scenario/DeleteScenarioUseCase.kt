package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DeleteScenarioUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val scenarioRepository: ScenarioRepository,
) {
    suspend operator fun invoke(scenarioNumber: Int) {
        teamRepository.currentTeam.first()?.let { team ->
            scenarioRepository.deleteTeamScenario(
                teamId = team.teamId,
                scenarioNumber = scenarioNumber
            )
        }
    }
}
package com.rumpilstilstkin.gloommaster.domain.usecase.scenario

import com.rumpilstilstkin.gloommaster.data.ScenarioRepository
import com.rumpilstilstkin.gloommaster.data.TeamRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RestoreScenarioUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val scenarioRepository: ScenarioRepository,
) {
    suspend operator fun invoke(scenarioNumber: Int) {
        teamRepository.currentTeam.first()?.let { team ->
            scenarioRepository.restoreTeamScenario(
                teamId = team.teamId,
                scenarioNumber = scenarioNumber,
            )
        }
    }
}

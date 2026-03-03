package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfoForSave
import javax.inject.Inject

class SaveTeamUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val scenarioRepository: ScenarioRepository
){
    suspend operator fun invoke(team: TeamInfoForSave) {
        val teamId = teamRepository.saveTeam(team)
        val scenario = scenarioRepository.getScenario(1)
        scenarioRepository.saveTeamScenario(scenario, teamId)
    }
}
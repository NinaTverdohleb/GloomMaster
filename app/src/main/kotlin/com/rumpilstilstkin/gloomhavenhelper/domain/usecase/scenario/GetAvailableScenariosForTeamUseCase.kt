package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioShortInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAvailableScenariosForTeamUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val scenarioRepository: ScenarioRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<ScenarioInfo>> =
        teamRepository.currentTeam.flatMapLatest { team ->
            if (team == null) {
                flowOf(emptyList())
            } else {
                val allScenarios = scenarioRepository.getAllScenarios().filter { scenario ->
                    scenario.pack in team.packs
                }
                scenarioRepository.getTeamScenariosFlow(team.teamId).map { scenarios ->
                    val teamScenarioNumbers = scenarios.map { it.scenarioNumber }.toSet()
                    allScenarios.filter { scenario ->
                        scenario.scenarioNumber !in teamScenarioNumbers
                    }
                }
            }
        }
}

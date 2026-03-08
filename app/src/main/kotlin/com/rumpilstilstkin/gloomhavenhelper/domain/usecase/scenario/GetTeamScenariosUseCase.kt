package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamScenarios
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTeamScenariosUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val filterTeamScenariosUseCase: FilterTeamScenariosUseCase
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<TeamScenarios> =
        teamRepository.currentTeam.flatMapLatest { team ->
            if (team == null) {
                flowOf(TeamScenarios(emptyList(), emptyList(), emptyList()))
            } else {
                teamRepository.getTeamWithScenarioFlow(team.teamId).map { teamInfoWithScenario ->
                    filterTeamScenariosUseCase(teamInfoWithScenario)
                }
            }
        }
}
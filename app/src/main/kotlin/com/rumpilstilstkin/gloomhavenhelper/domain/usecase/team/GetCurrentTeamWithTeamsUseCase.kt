package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Team
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetCurrentTeamWithTeamsUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<Pair<Team?, List<Team>>> =
        combine(
            teamRepository.currentTeam,
            teamRepository.getTeams(),
        ) { team, teams ->
            if (team == null) {
                null to emptyList()
            } else {
                val currentTeam =
                    Team(
                        teamId = team.teamId,
                        name = team.name,
                        packs = team.packs,
                        difficultyLevel = team.difficultyLevel,
                    )
                currentTeam to (teams - currentTeam)
            }
        }
}

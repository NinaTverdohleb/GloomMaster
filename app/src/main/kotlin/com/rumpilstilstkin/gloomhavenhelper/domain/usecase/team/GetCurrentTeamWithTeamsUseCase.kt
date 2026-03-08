package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetCurrentTeamWithTeamsUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<Pair<ShortTeamInfo?, List<ShortTeamInfo>>> =
        combine(
            teamRepository.currentTeam,
            teamRepository.getTeams()
        ) { team, teams ->
            if (team == null) {
                null to emptyList()
            } else {
                team to (teams - team)
            }
        }
}
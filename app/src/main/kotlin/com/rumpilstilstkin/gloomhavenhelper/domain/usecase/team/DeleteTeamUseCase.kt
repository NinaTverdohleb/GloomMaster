package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import javax.inject.Inject

class DeleteTeamUseCase@Inject constructor(
    private val teamRepository: TeamRepository,
) {
    suspend operator fun invoke(teamId: Int) {
        teamRepository.deleteTeam(teamId)
    }
}
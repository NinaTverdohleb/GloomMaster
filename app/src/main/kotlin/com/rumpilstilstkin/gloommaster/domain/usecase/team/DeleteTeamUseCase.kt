package com.rumpilstilstkin.gloommaster.domain.usecase.team

import com.rumpilstilstkin.gloommaster.data.TeamRepository
import javax.inject.Inject

class DeleteTeamUseCase@Inject constructor(
    private val teamRepository: TeamRepository,
) {
    suspend operator fun invoke(teamId: Int) {
        teamRepository.deleteTeam(teamId)
    }
}

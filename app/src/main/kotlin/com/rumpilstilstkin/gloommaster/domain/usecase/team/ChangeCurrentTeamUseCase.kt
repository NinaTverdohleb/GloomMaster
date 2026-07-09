package com.rumpilstilstkin.gloommaster.domain.usecase.team

import com.rumpilstilstkin.gloommaster.data.TeamRepository
import javax.inject.Inject

class ChangeCurrentTeamUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
) {
    suspend operator fun invoke(teamId: Int): Unit = teamRepository.setCurrentTeam(teamId)
}

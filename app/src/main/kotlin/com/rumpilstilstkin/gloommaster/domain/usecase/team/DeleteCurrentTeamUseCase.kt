package com.rumpilstilstkin.gloommaster.domain.usecase.team

import com.rumpilstilstkin.gloommaster.data.TeamRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DeleteCurrentTeamUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
) {
    suspend operator fun invoke() {
        val currentTeam = teamRepository.currentTeam.first() ?: return
        teamRepository.deleteTeam(currentTeam.teamId)
    }
}

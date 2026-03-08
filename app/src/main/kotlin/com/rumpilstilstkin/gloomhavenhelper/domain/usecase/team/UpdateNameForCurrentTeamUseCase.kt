package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

import android.util.Log
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UpdateNameForCurrentTeamUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
) {
    suspend operator fun invoke(name: String) {
        val currentTeam = teamRepository.currentTeam.first() ?: return
        if (currentTeam.name == name) return
        teamRepository.updateTeam(currentTeam.copy(name = name))
    }
}
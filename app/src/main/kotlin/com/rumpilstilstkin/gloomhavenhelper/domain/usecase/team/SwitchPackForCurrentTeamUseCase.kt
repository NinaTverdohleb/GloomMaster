package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SwitchPackForCurrentTeamUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
) {
    suspend operator fun invoke(packType: PackType) {
        val currentTeam = teamRepository.currentTeam.first() ?: return
        val newPacks = if (packType in currentTeam.packs) {
            currentTeam.packs - packType
        } else {
            currentTeam.packs + packType
        }
        teamRepository.updateTeam(currentTeam.copy(packs = newPacks))
    }
}
package com.rumpilstilstkin.gloommaster.domain.usecase.team

import com.rumpilstilstkin.gloommaster.data.TeamRepository
import com.rumpilstilstkin.gloommaster.domain.entity.ShortTeamInfo
import jakarta.inject.Inject

class GetTeamInfoUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
) {
    suspend operator fun invoke(teamId: Int): ShortTeamInfo? = teamRepository.getTeamInfo(teamId)
}

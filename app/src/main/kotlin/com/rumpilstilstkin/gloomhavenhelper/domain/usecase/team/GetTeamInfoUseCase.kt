package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team

import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
import jakarta.inject.Inject

class GetTeamInfoUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
) {
    suspend operator fun invoke(teamId: Int): ShortTeamInfo? = teamRepository.getTeamInfo(teamId)
}

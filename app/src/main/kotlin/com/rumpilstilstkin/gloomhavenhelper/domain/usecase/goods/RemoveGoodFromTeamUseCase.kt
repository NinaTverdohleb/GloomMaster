package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods

import com.rumpilstilstkin.gloomhavenhelper.data.GoodsRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RemoveGoodFromTeamUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val goodsRepository: GoodsRepository,
) {

    suspend operator fun invoke(goodNumber: Int) {
        val teamId = teamRepository.currentTeam.first()?.teamId ?: return
        goodsRepository.removeGoodFromTeam(teamId, goodNumber)
    }
}

package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods

import com.rumpilstilstkin.gloomhavenhelper.data.GoodsRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AddGoodToTeamUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val goodsRepository: GoodsRepository,
) {

    suspend operator fun invoke(goodNumber: Int) {
        val team = teamRepository.currentTeam.first() ?: return
        goodsRepository.addGoodToTeam(team.teamId, goodNumber)
    }
}

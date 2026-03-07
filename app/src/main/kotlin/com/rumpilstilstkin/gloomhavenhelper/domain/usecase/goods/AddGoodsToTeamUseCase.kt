package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods

import com.rumpilstilstkin.gloomhavenhelper.data.GoodsRepository
import javax.inject.Inject

class AddGoodsToTeamUseCase @Inject constructor(
    private val goodsRepository: GoodsRepository,
) {

    suspend operator fun invoke(teamId: Int, goodNumbers: List<Int>) {
        goodsRepository.addGoodsToTeam(teamId, goodNumbers)
    }
}

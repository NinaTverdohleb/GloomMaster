package com.rumpilstilstkin.gloommaster.domain.usecase.goods

import com.rumpilstilstkin.gloommaster.data.GoodsRepository
import javax.inject.Inject

class AddGoodsToTeamByNumbersUseCase @Inject constructor(
    private val goodsRepository: GoodsRepository,
) {
    suspend operator fun invoke(
        teamId: Int,
        goodNumbers: List<Int>,
    ) {
        val goodIds = goodsRepository.getGoodIdsByNumbers(goodNumbers)
        goodsRepository.addGoodsToTeam(teamId, goodIds.values.flatten())
    }
}

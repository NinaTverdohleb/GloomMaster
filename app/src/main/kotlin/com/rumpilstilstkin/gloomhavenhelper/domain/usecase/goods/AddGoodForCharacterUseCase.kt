package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods

import com.rumpilstilstkin.gloomhavenhelper.data.GoodsRepository
import javax.inject.Inject

class AddGoodForCharacterUseCase @Inject constructor(
    private val goodsRepository: GoodsRepository
) {
    suspend operator fun invoke(goodIds: List<Int>, characterId: Int) {
        goodsRepository.addCharacterGoods(
            characterId = characterId,
            goodIds = goodIds
        )
    }
}
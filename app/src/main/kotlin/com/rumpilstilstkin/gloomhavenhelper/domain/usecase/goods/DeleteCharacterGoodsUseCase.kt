package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods

import com.rumpilstilstkin.gloomhavenhelper.data.GoodsRepository
import javax.inject.Inject

class DeleteCharacterGoodsUseCase @Inject constructor(
    private val goodsRepository: GoodsRepository
) {
    suspend operator fun invoke(goodId: Int, characterId: Int) {
        goodsRepository.deleteCharacterGood(
            goodId = goodId,
            characterId = characterId
        )
    }

}
package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.GoodsRepository
import javax.inject.Inject

class BuyGoodForCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val goodsRepository: GoodsRepository
) {

    suspend operator fun invoke(goodIds: List<Int>, cost: Int, characterId: Int): Result<Unit> {
        return characterRepository.getCharacterById(characterId)?.let { character ->

            if (cost > character.goldCount) {
                Result.failure(Exception())
            }else {
                goodsRepository.addCharacterGoods(
                    characterId = characterId,
                    goodIds = goodIds
                )

                characterRepository.updateGold(characterId, character.goldCount - cost)
                Result.success(Unit)
            }
        } ?: Result.failure(Exception())
    }

}
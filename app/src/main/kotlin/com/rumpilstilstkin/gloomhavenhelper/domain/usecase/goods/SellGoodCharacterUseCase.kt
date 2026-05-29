package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.GoodsRepository
import javax.inject.Inject

class SellGoodCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val deleteCharacterGoodsUseCase: DeleteCharacterGoodsUseCase,
) {

    suspend operator fun invoke(goodNumber: Int, characterId: Int, cost: Int): Result<Unit> {
        return characterRepository.getCharacterById(characterId)?.let { character ->
            deleteCharacterGoodsUseCase(
                goodNumber = goodNumber,
                characterId = characterId
            )
            characterRepository.updateGold(characterId, character.goldCount + cost.div(2))
            Result.success(Unit)
        } ?: return Result.failure(Exception())
    }
}
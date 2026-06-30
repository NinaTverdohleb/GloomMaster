package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.goods

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import javax.inject.Inject

class SellGoodCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val deleteCharacterGoodsUseCase: DeleteCharacterGoodsUseCase,
) {
    suspend operator fun invoke(
        goodId: Int,
        characterId: Int,
        cost: Int,
    ): Result<Unit> {
        return characterRepository.getCharacterById(characterId)?.let { character ->
            deleteCharacterGoodsUseCase(
                goodId = goodId,
                characterId = characterId,
            )
            characterRepository.updateGold(characterId, character.goldCount + cost.div(2))
            Result.success(Unit)
        } ?: return Result.failure(Exception())
    }
}

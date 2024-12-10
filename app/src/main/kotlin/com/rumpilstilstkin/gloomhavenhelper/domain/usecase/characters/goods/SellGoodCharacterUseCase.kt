package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.goods

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import javax.inject.Inject

class SellGoodCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val deleteCharacterGoodsUseCase: DeleteCharacterGoodsUseCase,
) {

    suspend operator fun invoke(characterGoodId: Int, characterId: Int): Result<Unit> {
        val character = characterRepository.getCharacterById(characterId)
        val good = characterRepository.getCharacterGood(characterGoodId)
        deleteCharacterGoodsUseCase.invoke(characterGoodId = characterGoodId)
        characterRepository.updateGold(characterId, character.gold + good.cost.div(2))
        return Result.success(Unit)
    }
}
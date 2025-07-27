package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.goods

import android.util.Log
import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Good
import javax.inject.Inject

class BuyGoodForCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
) {

    suspend operator fun invoke(goods: List<Good>, characterId: Int): Result<Unit> {
        val character = characterRepository.getCharacterById(characterId)

        val goodsCost = goods.sumOf { it.cost }

        if (goodsCost > character.goldCount) {
            Log.d("Dto", "Не хватает денег, нужно $goodsCost, у вас ${character.goldCount}")
            return Result.failure(Exception())
        }

        goods.forEach { good ->
            characterRepository.addCharacterGood(
                characterId = characterId,
                goodId = good.id
            )
        }

        characterRepository.updateGold(characterId, character.goldCount - goodsCost)
        return Result.success(Unit)
    }

}
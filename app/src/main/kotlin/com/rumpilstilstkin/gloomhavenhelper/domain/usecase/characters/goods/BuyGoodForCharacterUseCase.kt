package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.goods

import android.util.Log
import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Good
import javax.inject.Inject

class BuyGoodForCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
) {

    suspend operator fun invoke(goodIdToCosts: List<Pair<Int, Int>>, characterId: Int): Result<Unit> {
        val character = characterRepository.getCharacterById(characterId)

        val goodsCost = goodIdToCosts.sumOf { it.second }

        if (goodsCost > character.goldCount) {
            return Result.failure(Exception())
        }

        goodIdToCosts.forEach { good ->
            characterRepository.addCharacterGood(
                characterId = characterId,
                goodId = good.first
            )
        }

        characterRepository.updateGold(characterId, character.goldCount - goodsCost)
        return Result.success(Unit)
    }

}
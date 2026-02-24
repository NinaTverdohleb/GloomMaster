package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.goods

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.GoodsRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Good
import javax.inject.Inject

class GetAvaliableCharacterGoodsUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val goodsRepository: GoodsRepository,
) {

    suspend operator fun invoke(characterId: Int): List<Good> {
        val character = characterRepository.getCharacterById(characterId)
        val characterGoods = characterRepository.getCharacterGoods(characterId)
        return if (character.teamId == null) {
            goodsRepository.getGoods()
                .filter { good -> good.id !in characterGoods.map { it.id } }
                .sortedBy { it.number }
                .distinctBy { it.number }
        } else {
            // TODO
            emptyList()
        }
    }
}
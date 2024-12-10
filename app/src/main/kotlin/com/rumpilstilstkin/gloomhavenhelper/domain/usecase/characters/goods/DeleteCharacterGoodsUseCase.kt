package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.goods

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import javax.inject.Inject

class DeleteCharacterGoodsUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    suspend operator fun invoke(characterGoodId: Int) {
        characterRepository.deleteCharacterGood( characterGoodId = characterGoodId)
    }

}
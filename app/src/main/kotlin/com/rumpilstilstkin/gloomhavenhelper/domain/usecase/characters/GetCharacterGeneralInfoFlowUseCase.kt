package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import javax.inject.Inject

class GetCharacterGeneralInfoFlowUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    operator fun invoke(characterId: Int) =
        characterRepository.getCharacterByIdFlow(characterId)
}
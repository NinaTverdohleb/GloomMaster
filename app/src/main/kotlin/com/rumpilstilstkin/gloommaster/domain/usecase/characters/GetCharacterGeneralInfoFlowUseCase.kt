package com.rumpilstilstkin.gloommaster.domain.usecase.characters

import com.rumpilstilstkin.gloommaster.data.CharacterRepository
import javax.inject.Inject

class GetCharacterGeneralInfoFlowUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
) {
    operator fun invoke(characterId: Int) = characterRepository.getCharacterByIdFlow(characterId)
}

package com.rumpilstilstkin.gloommaster.domain.usecase.characters

import com.rumpilstilstkin.gloommaster.data.CharacterRepository
import javax.inject.Inject

class GetCharacterGeneralInfoUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
) {
    suspend operator fun invoke(characterId: Int) = characterRepository.getCharacterById(characterId)
}

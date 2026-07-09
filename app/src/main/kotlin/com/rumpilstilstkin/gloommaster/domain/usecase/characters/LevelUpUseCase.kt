package com.rumpilstilstkin.gloommaster.domain.usecase.characters

import com.rumpilstilstkin.gloommaster.data.CharacterRepository
import javax.inject.Inject

class LevelUpUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
) {
    suspend operator fun invoke(characterId: Int) {
        characterRepository.getCharacterById(characterId)?.also { character ->
            characterRepository.updateLevel(characterId, character.level + 1)
        }
    }
}

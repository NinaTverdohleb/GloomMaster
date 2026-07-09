package com.rumpilstilstkin.gloommaster.domain.usecase.characters

import com.rumpilstilstkin.gloommaster.data.CharacterRepository
import javax.inject.Inject

class UpdateCharacterNameUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
) {
    suspend operator fun invoke(
        characterId: Int,
        name: String,
    ) {
        characterRepository.updateName(characterId, name)
    }
}

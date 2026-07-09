package com.rumpilstilstkin.gloommaster.domain.usecase.characters.perks

import com.rumpilstilstkin.gloommaster.data.CharacterRepository
import javax.inject.Inject

class DeleteCharacterPerkUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
) {
    suspend operator fun invoke(
        characterId: Int,
        perkId: Int,
    ) {
        characterRepository.deleteCharacterPerk(
            characterId = characterId,
            perkId = perkId,
        )
    }
}

package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.perks

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
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

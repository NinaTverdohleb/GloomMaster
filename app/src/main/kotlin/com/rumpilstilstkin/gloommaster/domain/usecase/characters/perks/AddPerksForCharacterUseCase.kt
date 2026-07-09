package com.rumpilstilstkin.gloommaster.domain.usecase.characters.perks

import com.rumpilstilstkin.gloommaster.data.CharacterRepository
import javax.inject.Inject

class AddPerksForCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
) {
    suspend operator fun invoke(
        perksIds: List<Int>,
        characterId: Int,
    ) {
        perksIds.forEach { perkId ->
            characterRepository.addCharacterPerk(
                characterId = characterId,
                perkId = perkId,
            )
        }
    }
}

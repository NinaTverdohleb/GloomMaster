package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import javax.inject.Inject

class UpdateNotesUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
) {
    suspend operator fun invoke(
        characterId: Int,
        newNotes: String,
    ) {
        characterRepository.updateNotes(characterId, newNotes)
    }
}

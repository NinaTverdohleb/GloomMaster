package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import javax.inject.Inject

class MarksCheckedChangeUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(characterId: Int, isChecked: Boolean) {
        val character = characterRepository.getCharacterById(characterId)
        val newCheckMarkCount = if (isChecked) {
            if (character.checkMarkCount < 18) character.checkMarkCount + 1 else 18
        } else {
            if (character.checkMarkCount != 0) character.checkMarkCount - 1 else 0
        }
        characterRepository.updateCheckMarks(characterId, newCheckMarkCount)
    }
}

package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.getExpForLevel
import javax.inject.Inject

class UpdateCharacterLevelUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(characterId: Int, level: Int) {
        characterRepository.setLevel(
            id = characterId,
            level = level,
            experience = getExpForLevel(level)
        )
    }
}
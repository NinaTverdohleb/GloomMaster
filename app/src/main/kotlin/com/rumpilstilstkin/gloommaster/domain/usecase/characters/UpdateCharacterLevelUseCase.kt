package com.rumpilstilstkin.gloommaster.domain.usecase.characters

import com.rumpilstilstkin.gloommaster.data.CharacterRepository
import com.rumpilstilstkin.gloommaster.domain.getExpForLevel
import javax.inject.Inject

class UpdateCharacterLevelUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
) {
    suspend operator fun invoke(
        characterId: Int,
        level: Int,
    ) {
        characterRepository.setLevel(
            id = characterId,
            level = level,
            experience = getExpForLevel(level),
        )
    }
}

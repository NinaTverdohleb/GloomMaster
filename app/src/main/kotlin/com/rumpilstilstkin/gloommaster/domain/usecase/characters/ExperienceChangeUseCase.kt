package com.rumpilstilstkin.gloommaster.domain.usecase.characters

import com.rumpilstilstkin.gloommaster.data.CharacterRepository
import javax.inject.Inject

class ExperienceChangeUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
) {
    suspend operator fun invoke(
        characterId: Int,
        newExp: Int,
    ) {
        characterRepository.updateExperience(characterId, newExp)
    }
}

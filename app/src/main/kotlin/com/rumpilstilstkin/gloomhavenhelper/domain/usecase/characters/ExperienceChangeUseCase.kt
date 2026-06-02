package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
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

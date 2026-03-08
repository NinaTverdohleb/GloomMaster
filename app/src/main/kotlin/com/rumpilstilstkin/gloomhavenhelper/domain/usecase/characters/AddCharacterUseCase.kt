package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterForSave
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val teamRepository: TeamRepository,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend operator fun invoke(
        name: String,
        level: Int,
        characterType: CharacterClassType
    ) {
        val team = teamRepository.currentTeam.first() ?: return
        val character = CharacterForSave(
            name = name,
            level = level,
            characterType = characterType,
            teamId = team.teamId
        )
        characterRepository.addCharacter(character)
    }
}
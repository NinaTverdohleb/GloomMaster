package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.classes

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterClassRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AddCharacterClassForTeamUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val characterClassRepository: CharacterClassRepository,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend operator fun invoke(type: CharacterClassType) {
        val team = teamRepository.currentTeam.first() ?: return
        characterClassRepository.addAvailableClass(
            teamId = team.teamId,
            type = type
        )
    }
}
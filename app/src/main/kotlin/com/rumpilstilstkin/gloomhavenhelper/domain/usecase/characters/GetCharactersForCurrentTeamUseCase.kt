package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters

import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class GetCharactersForCurrentTeamUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val teamRepository: TeamRepository,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<CharacterInfo>> =
        teamRepository.currentTeamId
            .flatMapLatest { teamId ->
                characterRepository.getCharacterByTeamId(teamId)
            }
}
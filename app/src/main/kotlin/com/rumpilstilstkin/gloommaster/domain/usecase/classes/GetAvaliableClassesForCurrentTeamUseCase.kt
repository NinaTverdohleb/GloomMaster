package com.rumpilstilstkin.gloommaster.domain.usecase.classes

import com.rumpilstilstkin.gloommaster.data.CharacterClassRepository
import com.rumpilstilstkin.gloommaster.data.TeamRepository
import com.rumpilstilstkin.gloommaster.domain.entity.CharacterClassType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetAvaliableClassesForCurrentTeamUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val characterClassRepository: CharacterClassRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<CharacterClassType>> =
        teamRepository.currentTeam
            .flatMapLatest { team ->
                if (team == null) {
                    flowOf(
                        listOf(
                            CharacterClassType.Brute,
                            CharacterClassType.Tinkerer,
                            CharacterClassType.Spellweaver,
                            CharacterClassType.Scoundrel,
                            CharacterClassType.Cragheart,
                            CharacterClassType.Mindthief,
                        ),
                    )
                } else {
                    characterClassRepository.getAvailableClassesForTeam(team.teamId)
                }
            }
}

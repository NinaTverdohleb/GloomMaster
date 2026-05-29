package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

import com.rumpilstilstkin.gloomhavenhelper.data.MonsterRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Magic
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMagic
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.utils.toResult
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CreateActiveScenarioUseCase @Inject constructor(
    private val getCurrentTeamUseCase: GetCurrentTeamUseCase,
    private val monsterRepository: MonsterRepository,
    private val saveScenarioStateUseCase: SaveScenarioStateUseCase,
    private val clearCurrentActiveScenarioUseCase: ClearCurrentActiveScenarioUseCase
) {
    suspend operator fun invoke(
        scenarioNumber: Int?,
    ): Result<Unit> {
        getCurrentTeamUseCase().first().let { team ->
            if (team != null) {
                clearCurrentActiveScenarioUseCase()
                val monsters = scenarioNumber?.let { _ ->
                    monsterRepository.getMonsterSlugsForScenario(scenarioNumber)
                } ?: emptyList()
                val monstersCard = monsterRepository.getMonsterCards(monsters)
                val state = ScenarioGameState(
                    scenarioNumber = scenarioNumber,
                    monsterSlugs = monsters,
                    round = 0,
                    availableCards =  monstersCard.distinct().map { it.cardId },
                    activeMonsters = emptyList(),
                    magicCharges = listOf(
                        ScenarioGameStateMagic(
                            name = Magic.FIRE.name,
                            value = 0
                        ),
                        ScenarioGameStateMagic(
                            name = Magic.FROST.name,
                            value = 0
                        ),
                        ScenarioGameStateMagic(
                            name = Magic.AIR.name,
                            value = 0
                        ),
                        ScenarioGameStateMagic(
                            name = Magic.EARTH.name,
                            value = 0
                        ),
                        ScenarioGameStateMagic(
                            name = Magic.SUN.name,
                            value = 0
                        ),
                        ScenarioGameStateMagic(
                            name = Magic.MOON.name,
                            value = 0
                        ),
                    )
                )
                saveScenarioStateUseCase(state)
                return Unit.toResult()
            } else {
                return Result.failure(IllegalStateException("Team is null"))
            }
        }
    }
}
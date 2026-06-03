package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play

import com.rumpilstilstkin.gloomhavenhelper.data.MonsterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioGameStateRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.LevelInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.builders.buildScenarioBattleState
import javax.inject.Inject

class RestoreScenarioStateUseCase @Inject constructor(
    private val scenarioGameStateRepository: ScenarioGameStateRepository,
    private val monsterRepository: MonsterRepository,
    private val scenarioRepository: ScenarioRepository,
) {
    suspend operator fun invoke(
        team: TeamInfo,
        levelInfo: LevelInfo?,
        locale: String,
    ): ScenarioBattleState {
        val state = scenarioGameStateRepository.get()
        return state?.let { gameState ->
            val scenarioName =
                gameState.scenarioNumber?.let {
                    scenarioRepository.getScenario(gameState.scenarioNumber, locale).scenarioName
                } ?: ""
            val monsterBySlug =
                monsterRepository.getMonstersBySlugs(gameState.monsterSlugs, team.level, locale)
                    .associateBy { it.slug }

            val cardByKey =
                monsterBySlug
                    .values
                    .asSequence()
                    .flatMap { it.cards.asSequence() }
                    .associateBy { it.deckName to it.cardId }

            val availableCard =
                gameState.availableCards
                    .mapNotNull { (deck, cardId) -> cardByKey[deck to cardId] }
            buildScenarioBattleState {
                levelInfo?.let { levelInfo(it) }
                name(scenarioName)
                gamersCount(team.aliveCharacters.size)
                cards(availableCard)

                round(state.round)
                magicState(state.magicCharges)
                scenarioNumber(state.scenarioNumber)

                availableEffects(team.packs)
                monsters(monsterBySlug)
                activeMonsters {
                    scenarioMonsters(monsterBySlug)
                    activeMonsters(state.activeMonsters)
                    cards(cardByKey)
                    levels(state.level to team.level)
                    additionalMonster { level, slug ->
                        monsterRepository
                            .getMonstersBySlugs(
                                slugs = listOf(slug),
                                level = level,
                                locale = locale,
                            ).firstOrNull()
                    }
                }
            }
        }
            ?: buildScenarioBattleState {
                levelInfo?.let { levelInfo(it) }
                gamersCount(team.aliveCharacters.size)
                availableEffects(team.packs)
            }
    }
}

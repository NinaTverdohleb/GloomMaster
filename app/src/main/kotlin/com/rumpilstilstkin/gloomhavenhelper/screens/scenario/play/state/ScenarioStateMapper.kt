package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AvailableCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMonsterItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterName
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap

object ScenarioStateMapper {
    fun toUiState(state: ScenarioBattleState): ScenarioStateUi {
        val existingSlugs = state.activeMonsters.keys
        val monsters =
            state.activeMonsters.values
                .map { monster ->
                    val units =
                        monster
                            .units
                            .mapKeys { (number, monster) ->
                                UnitCompact(number, monster.isSpecial)
                            }.mapValues { (_, monster) ->
                                monster.toUi()
                            }.toSortedMap()
                            .toImmutableMap()
                    MonsterItemUi(
                        slug = monster.slug,
                        name = monster.name,
                        isFly = monster.isFly,
                        currentCard = monster.currentCard,
                        units = units,
                        isBoss = monster.isBoss,
                    )
                }.sortedBy { it.currentCard?.initiative }
                .toImmutableList()
        val smallestInitiative = monsters.firstOrNull()?.currentCard?.initiative ?: 0
        return ScenarioStateUi(
            scenarioName = state.name,
            scenarioNumber = state.scenarioNumber,
            exp = state.exp,
            gold = state.golds,
            trapDamage = state.trapDamage,
            level = state.monsterLevel,
            round = state.round,
            monsters = monsters,
            smallestInitiative = smallestInitiative,
            monstersForAdd =
                state.monsters
                    .filterKeys { it !in existingSlugs }
                    .map { (_, monster) ->
                        MonsterName(
                            slug = monster.slug,
                            name = monster.name,
                        )
                    }.toImmutableList(),
            magicChargeList = state.magicState.charges.toImmutableMap(),
        )
    }

    fun stateForSave(state: ScenarioBattleState) =
        ScenarioGameState(
            monsterSlugs = state.monsters.keys.toList(),
            round = state.round,
            availableCards = state.deck.toAvailableCards(),
            activeMonsters =
                state.activeMonsters.values.map { monsterItem ->
                    ScenarioGameStateMonsterItem(
                        slug = monsterItem.slug,
                        currentCard =
                            monsterItem.currentCard?.let {
                                AvailableCard(
                                    deck = monsterItem.deck,
                                    cardId = monsterItem.currentCard.cardId,
                                )
                            },
                        units =
                            monsterItem.units.values.map { unit ->
                                ScenarioGameStateMonsterUnit(
                                    number = unit.number,
                                    currentLife = unit.currentLife,
                                    level = unit.level,
                                    isElite = unit.isSpecial,
                                    effects = unit.effects.filterValues { it }.keys,
                                    maxLife = unit.maxLife,
                                )
                            },
                    )
                },
            magicCharges = state.magicState.toSaveState(),
            scenarioNumber = state.scenarioNumber,
            level = state.generalLevel,
        )
}

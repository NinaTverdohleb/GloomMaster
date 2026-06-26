package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AvailableCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMonsterItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.collections.immutable.toPersistentSet

object ScenarioStateMapper {
    fun toUiState(state: ScenarioBattleState): ScenarioStateUi {
        val existingSlugs = state.activeMonsters.map { it.slug }.toSet()
        return ScenarioStateUi(
            scenarioName = state.name,
            scenarioNumber = state.scenarioNumber,
            exp = state.exp,
            gold = state.golds,
            trapDamage = state.trapDamage,
            level = state.monsterLevel,
            round = state.round,
            monsters =
                state.activeMonsters
                    .map { monster ->
                        val units = monster.units
                            .associateBy { UnitCompact(it.number, it.isSpecial) }
                            .toSortedMap()
                            .toImmutableMap()
                        MonsterItemUi(
                            slug = monster.slug,
                            name = monster.name,
                            isFly = monster.isFly,
                            currentCard = monster.currentCard,
                            units = units,
                            isBoss = monster.isBoss
                        )
                    }
                    .toImmutableList(),
            monstersForAdd =
                state.monsters
                    .filterKeys { it !in existingSlugs }
                    .map { (_, monster) ->
                        MonsterItem(
                            slug = monster.slug,
                            name = monster.name,
                            isFly = monster.isFly,
                            currentCard = null,
                            deck = monster.deckName,
                        )
                    }.toImmutableList(),
            magicChargeList = state.magicState.charges.toImmutableMap(),
            availableEffects = state.availableEffects.toPersistentSet(),
        )
    }

    fun stateForSave(state: ScenarioBattleState) =
        ScenarioGameState(
            monsterSlugs = state.monsters.keys.toList(),
            round = state.round,
            availableCards = state.deck.toAvailableCards(),
            activeMonsters =
                state.activeMonsters.map { monsterItem ->
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
                            monsterItem.units.map { unit ->
                                ScenarioGameStateMonsterUnit(
                                    number = unit.number,
                                    currentLife = unit.currentLife,
                                    level = unit.level,
                                    isElite = unit.isSpecial,
                                    effects = unit.effects,
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

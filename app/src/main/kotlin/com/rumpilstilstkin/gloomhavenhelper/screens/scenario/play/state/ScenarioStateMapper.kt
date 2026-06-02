package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AvailableCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMonsterItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap

object ScenarioStateMapper {
    fun toUiState(state: ScenarioBattleState): ScenarioStateUi {
        val existingSlugs = state.activeMonsters.map { it.slug }.toSet()
        return ScenarioStateUi(
            name = state.name,
            exp = state.exp,
            gold = state.golds,
            trapDamage = state.trapDamage,
            level = state.monsterLevel,
            round = state.round,
            monsters =
                state.activeMonsters
                    .map { monster ->
                        monster.copy(
                            units =
                                monster.units
                                    .sortedWith(
                                        compareByDescending<MonsterUnit> { it.isSpecial }
                                            .thenBy { it.number },
                                    ).toImmutableList(),
                        )
                    }.sortedBy { it.currentCard?.initiative ?: 0 }
                    .toImmutableList(),
            monstersForAdd =
                state.monsters
                    .filter { it.slug !in existingSlugs }
                    .map {
                        MonsterItem(
                            slug = it.slug,
                            name = it.name,
                            isFly = it.isFly,
                            currentCard = null,
                            deck = it.deckName,
                        )
                    }.toImmutableList(),
            magicChargeList = state.magicState.charges.toImmutableMap(),
            availableEffects = state.availableEffects,
        )
    }

    fun stateForSave(state: ScenarioBattleState) =
        ScenarioGameState(
            monsterSlugs = state.monsters.map { it.slug },
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

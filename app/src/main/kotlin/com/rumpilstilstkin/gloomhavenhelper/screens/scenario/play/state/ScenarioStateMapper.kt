package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioBattleInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMagic
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMonsterItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterAbilityCard
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterItem
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterUnit
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap

object ScenarioStateMapper {

    fun toUiState(state: ScenarioLogicState): ScenarioStateUi {
        val existingIds = state.activeMonsters.map { it.id }.toSet()
        return ScenarioStateUi(
            name = state.scenarioInfo.name,
            exp = state.scenarioInfo.exp,
            gold = state.scenarioInfo.golds,
            trapDamage = state.scenarioInfo.trapDamage,
            round = state.round,
            monsters = state.activeMonsters.map { monster ->
                monster.copy(
                    units = monster.units.sortedWith(
                        compareByDescending<MonsterUnit> { it.isSpecial }
                            .thenBy { it.number }
                    ).toImmutableList()
                )
            }.sortedBy { it.currentCard?.initiative ?: 0 }.toImmutableList(),
            showMonsterDialog = state.showMonsterDialog,
            monstersForAdd = state.scenarioInfo.monsters
                .filter { it.id !in existingIds }
                .map {
                    MonsterItem(
                        id = it.id,
                        name = it.name,
                        isFly = it.isFly,
                        currentCard = null,
                    )
                }
                .toImmutableList(),
            magicChargeList = state.magicState.toMap().toImmutableMap(),
            showUnitLevelDialog = state.showUnitLevelDialog
        )
    }

    fun restore(battleInfo: ScenarioBattleInfo): ScenarioLogicState =
        ScenarioLogicState(
            scenarioInfo = battleInfo,
            cardDeck = CardDeckState.create(battleInfo.availableCards),
            activeMonsters = battleInfo.activeMonsters.map { item ->
                val monster = battleInfo.monsters.first { it.id == item.id }
                MonsterItem(
                    id = item.id,
                    name = monster.name,
                    currentCard = item.currentCard?.let { cardId ->
                        battleInfo.availableCards
                            .firstOrNull { card -> card.cardId == cardId }
                            ?.let { MonsterAbilityCard.createFromMonsterCard(it) }
                    },
                    isFly = monster.isFly,
                    isBoss = monster.isBoss,
                    units = item.units.map { stateUnit ->
                        MonsterUnit.create(
                            monster = monster,
                            number = stateUnit.number,
                            isElite = stateUnit.isElite,
                            currentLife = stateUnit.currentLife
                        )
                    }.toImmutableList()
                )
            },
            round = battleInfo.round,
            showMonsterDialog = false,
            showUnitLevelDialog = false,
            magicState = MagicState.restore(battleInfo.magicCharges)
        )

    fun stateForSave(state: ScenarioLogicState) =
        ScenarioGameState(
            name = state.scenarioInfo.name,
            monsterNames = state.scenarioInfo.monsters.map { it.name },
            round = state.round,
            availableCards = state.cardDeck.getCards().map { it.cardId },
            activeMonsters = state.activeMonsters.map { monsterItem ->
                ScenarioGameStateMonsterItem(
                    id = monsterItem.id,
                    currentCard = monsterItem.currentCard?.id,
                    units = monsterItem.units.map { unit ->
                        ScenarioGameStateMonsterUnit(
                            number = unit.number,
                            currentLife = unit.currentLife,
                            level = unit.level,
                            isElite = unit.isSpecial
                        )
                    },
                )
            },
            magicCharges = state.magicState.toList().map { (name, value) ->
                ScenarioGameStateMagic(
                    name = name,
                    value = value
                )
            }
        )
}

package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioBattleInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMagic
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMonsterItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ActionUi
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterAbilityCard
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterItem
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterUnit
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap

object ScenarioStateMapper {

    fun toUiState(state: ScenarioLogicState): ScenarioStateUi {
        val existingSlugs = state.activeMonsters.map { it.slug }.toSet()
        return ScenarioStateUi(
            name = state.scenarioInfo.name,
            exp = state.scenarioInfo.exp,
            gold = state.scenarioInfo.golds,
            trapDamage = state.scenarioInfo.trapDamage,
            level = state.scenarioInfo.monsterLevel,
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
                .filter { it.slug !in existingSlugs }
                .map {
                    MonsterItem(
                        slug = it.slug,
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
                val monster = battleInfo.monsters.first { it.slug == item.slug }
                MonsterItem(
                    slug = item.slug,
                    name = monster.name,
                    currentCard = item.currentCard?.let { cardId ->
                        battleInfo.monsters.flatMap { it.cards }
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
                            currentLife = stateUnit.currentLife,
                            effects = stateUnit.effects.map {
                                ActionUi.fromMonsterStatType(it)
                            }.toImmutableList()
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
            monsterNames = state.scenarioInfo.monsters.map { it.name },
            round = state.round,
            availableCards = state.cardDeck.getCards().map { it.cardId },
            activeMonsters = state.activeMonsters.map { monsterItem ->
                ScenarioGameStateMonsterItem(
                    slug = monsterItem.slug,
                    currentCard = monsterItem.currentCard?.id,
                    units = monsterItem.units.map { unit ->
                        ScenarioGameStateMonsterUnit(
                            number = unit.number,
                            currentLife = unit.currentLife,
                            level = unit.level,
                            isElite = unit.isSpecial,
                            effects = unit.effects.map { it.toLogic() }
                        )
                    },
                )
            },
            magicCharges = state.magicState.toList().map { (name, value) ->
                ScenarioGameStateMagic(
                    name = name,
                    value = value
                )
            },
            scenarioNumber = state.scenarioInfo.scenarioNumber
        )
}

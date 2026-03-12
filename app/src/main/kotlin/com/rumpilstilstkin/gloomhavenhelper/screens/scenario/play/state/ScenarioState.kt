package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioBattleInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStats
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ActionUi
import com.rumpilstilstkin.gloomhavenhelper.screens.models.EffectItem
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterAbilityCard
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterItem
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterUnit
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.Serializable

@Serializable
data class ScenarioLogicState(
    val scenarioInfo: ScenarioBattleInfo,
    val cardDeck: CardDeckState,
    val activeMonsters: List<MonsterItem> = listOf(),
    val round: Int = 0,
    val showMonsterDialog: Boolean = false,
    val showUnitLevelDialog: Boolean = false,
    val magicState: MagicState = MagicState.initial()
) {
    fun updateMagic(magic: Magic): ScenarioLogicState =
        copy(magicState = magicState.toggle(magic))

    fun toUIState(): ScenarioStateUi = ScenarioStateMapper.toUiState(this)

    fun addMonster(monsterIds: List<Int>): ScenarioLogicState {
        val newMonsterItems = monsterIds.map { monsterId ->
            val monster = scenarioInfo.monsters.first { it.id == monsterId }
            val units = if (monster.isBoss) {
                persistentListOf(MonsterUnit.createBoss(monster, scenarioInfo.gamersCount))
            } else {
                persistentListOf()
            }
            MonsterItem(
                id = monster.id,
                isFly = monster.isFly,
                name = monster.name,
                currentCard = null,
                isBoss = monster.isBoss,
                units = units,
            )
        }
        var state = this
            .copy(activeMonsters = (activeMonsters + newMonsterItems).toImmutableList())

        newMonsterItems.forEach { monster ->
            state = state.updateMonsterCard(monster)
        }
        return state
    }

    fun removeMonster(monsterId: Int): ScenarioLogicState =
        this.copy(
            activeMonsters = activeMonsters.filter { it.id != monsterId }.toImmutableList()
        )

    fun addUnits(numbers: List<Int>, monsterId: Int, isSpecial: Boolean): ScenarioLogicState {
        val monster = scenarioInfo.monsters.first { it.id == monsterId }
        val newUnits = numbers.map { number ->
            MonsterUnit.create(monster, number, isSpecial)
        }
        return copy(
            activeMonsters = activeMonsters
                .updateMonster(monsterId) { it.addUnits(newUnits) }
                .toImmutableList()
        )
    }

    fun removeUnit(number: Int, monsterId: Int): ScenarioLogicState =
        copy(
            activeMonsters = activeMonsters
                .updateMonster(monsterId) { it.filterUnits { unit -> unit.number != number } }
                .toImmutableList()
        )

    fun nextRound(): ScenarioLogicState {
        var state = this.copy(
            round = round + 1
        )
        this.activeMonsters.forEach {
            state = state.updateMonsterCard(it)
        }
        return state.copy(
            magicState = magicState.decreaseAll()
        )
    }

    fun updateUnitStats(monsterId: Int, number: Int, stats: MonsterStats): ScenarioLogicState =
        copy(
            activeMonsters = activeMonsters
                .updateMonster(monsterId) { monster ->
                    monster.updateUnit(number) { unit ->
                        unit.copy(
                            stats = stats.stats.map { EffectItem.fromCardAction(it) }
                                .toImmutableList(),
                            currentLife = stats.life,
                            maxLife = stats.life,
                            level = stats.level
                        )
                    }
                }
                .toImmutableList()
        )

    fun updateUnitLife(monsterId: Int, number: Int, newValue: Int): ScenarioLogicState =
        copy(
            activeMonsters = activeMonsters
                .updateMonster(monsterId) { monster ->
                    monster.updateUnit(number) { it.copy(currentLife = newValue) }
                }
                .toImmutableList()
        )

    fun addEffect(monsterId: Int, number: Int, effect: ActionUi): ScenarioLogicState =
        copy(
            activeMonsters = activeMonsters
                .updateMonster(monsterId) { monster ->
                    monster.updateUnit(number) { unit ->
                        val newEffects = if (unit.effects.contains(effect)) {
                            unit.effects - effect
                        } else {
                            unit.effects + effect
                        }
                        unit.copy(effects = newEffects.toImmutableList())
                    }
                }
                .toImmutableList()
        )

    private fun updateMonsterCard(monster: MonsterItem): ScenarioLogicState {
        if (monster.units.isEmpty()) return this
        val monster = scenarioInfo.monsters.first { it.id == monster.id }
        val drawResult = cardDeck.drawCard(monster.deckName)

        return copy(
            activeMonsters = activeMonsters
                .updateMonster(monster.id) { m ->
                    m.copy(currentCard = drawResult.card?.let {
                        MonsterAbilityCard.createFromMonsterCard(
                            it
                        )
                    })
                },
            cardDeck = drawResult.newState
        )
    }

    companion object {
        fun restore(state: ScenarioBattleInfo): ScenarioLogicState =
            ScenarioStateMapper.restore(state)
    }
}

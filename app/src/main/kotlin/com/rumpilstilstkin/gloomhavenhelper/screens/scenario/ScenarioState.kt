package com.rumpilstilstkin.gloomhavenhelper.screens.scenario

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioBattleInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ActionUi
import com.rumpilstilstkin.gloomhavenhelper.screens.models.EffectItem
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterAbilityCard
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterItem
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.ui.icons.GameIcons
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap

@Immutable
data class ScenarioLogicState(
    val scenarioInfo: ScenarioBattleInfo,
    val availableCards: ImmutableList<MonsterCard>,
    val activeMonsters: ImmutableList<MonsterItem> = persistentListOf(),
    val round: Int = 0,
    val showMonsterDialog: Boolean = false,
    val magicChargeMap: ImmutableMap<Magic, MagicValue> = persistentMapOf(
        Magic.FIRE to MagicValue(),
        Magic.FROST to MagicValue(),
        Magic.AIR to MagicValue(),
        Magic.EARTH to MagicValue(),
        Magic.SUN to MagicValue(),
        Magic.MOON to MagicValue(),
    )
) {
    fun updateMagic(magic: Magic): ScenarioLogicState {
        val currentValue = magicChargeMap[magic] ?: return this
        val newValue = currentValue.update()
        return this.copy(
            magicChargeMap = magicChargeMap.plus(magic to newValue).toImmutableMap()
        )
    }

    fun toUIState(): ScenarioUIState {
        val existingIds = activeMonsters.map { it.id }.toSet()
        return ScenarioUIState(
            name = scenarioInfo.name,
            exp = scenarioInfo.exp,
            gold = scenarioInfo.golds,
            trapDamage = scenarioInfo.trapDamage,
            round = round,
            monsters = activeMonsters.map { monster ->
                monster.copy(
                    units = monster.units.sortedWith(
                        compareByDescending<MonsterUnit> { it.isSpecial }
                            .thenBy { it.number }
                    ).toImmutableList()
                )
            },
            showMonsterDialog = showMonsterDialog,
            monstersForAdd = scenarioInfo.monsters.filter { it.id !in existingIds }.map {
                MonsterItem(
                    id = it.id,
                    name = it.name,
                    isFly = it.isFly,
                    currentCard = null,
                )
            },
            magicChargeList = magicChargeMap
        )
    }

    fun addMonster(monsterIds: List<Int>): ScenarioLogicState {
        val newMonsterItems = monsterIds.map { monsterId ->
            val newMonster = scenarioInfo.monsters.first { it.id == monsterId }
            if (newMonster.isBoss) {
                val maxLife = newMonster.life * scenarioInfo.gamersCount
                MonsterItem(
                    id = newMonster.id,
                    isFly = newMonster.isFly,
                    name = newMonster.name,
                    currentCard = null,
                    isBoss = true,
                    units = persistentListOf(
                        MonsterUnit(
                            number = 1,
                            maxLife = maxLife,
                            currentLife = maxLife,
                            stats = newMonster.stats.map {
                                EffectItem.fromCardAction(it)
                            }.toImmutableList(),
                            isSpecial = false,
                            immunity = newMonster
                                .immunity
                                .map { ActionUi.fromMonsterStatType(it) }
                                .toImmutableList()
                        )
                    )
                )
            } else {
                MonsterItem(
                    id = newMonster.id,
                    name = newMonster.name,
                    currentCard = null,
                    isBoss = false,
                    isFly = newMonster.isFly
                )
            }
        }
        var state = this
            .copy(activeMonsters = (activeMonsters + newMonsterItems).toImmutableList())

        monsterIds.forEach { monsterId ->
            state = state.updateMonsterCard(monsterId)
        }
        return state
    }

    fun removeMonster(monsterId: Int): ScenarioLogicState =
        this.copy(
            activeMonsters = activeMonsters.filter { it.id != monsterId }.toImmutableList()
        )

    fun addUnits(numbers: List<Int>, monsterId: Int, isSpecial: Boolean): ScenarioLogicState {
        val monster = scenarioInfo.monsters.first { it.id == monsterId }
        val maxLife = if (isSpecial) monster.eliteLife else monster.life
        val stats = if (isSpecial) monster.eliteStats else monster.stats
        val newUnits = numbers.map { number ->
            MonsterUnit(
                number = number,
                maxLife = maxLife,
                currentLife = maxLife,
                stats = stats.map { EffectItem.fromCardAction(it) }
                    .toImmutableList(),
                isSpecial = isSpecial,
            )
        }
        return this.copy(
            activeMonsters = activeMonsters.map {
                if (it.id == monsterId) {
                    it.copy(
                        units = (it.units + newUnits).toImmutableList()
                    )
                } else {
                    it
                }
            }.toImmutableList()
        )
    }

    fun removeUnit(number: Int, monsterId: Int): ScenarioLogicState {
        return this.copy(
            activeMonsters = activeMonsters.map { monsterItem ->
                if (monsterItem.id == monsterId) {
                    monsterItem.copy(
                        units = monsterItem
                            .units
                            .filter { it.number != number }
                            .toImmutableList()
                    )
                } else {
                    monsterItem
                }
            }.toImmutableList()
        )
    }

    fun nextRound(): ScenarioLogicState {
        var state = this.copy(
            round = round + 1
        )
        this.activeMonsters.forEach {
            state = state.updateMonsterCard(it.id)
        }
        var chargeList = this.magicChargeMap
        this.magicChargeMap.keys.forEach { magic ->
            val newValue = magicChargeMap[magic]?.decrease()
            if (newValue != null) {
                chargeList = chargeList.plus(magic to newValue).toImmutableMap()
            }
        }
        return state.copy(
            magicChargeMap = chargeList
        )

    }

    fun updateUnitLife(monsterId: Int, number: Int, newValue: Int): ScenarioLogicState {
        return this.copy(
            activeMonsters = activeMonsters.map { monsterItem ->
                if (monsterItem.id == monsterId) {
                    monsterItem.copy(
                        units = monsterItem.units
                            .map {
                                if (it.number == number) {
                                    it.copy(
                                        currentLife = newValue
                                    )
                                } else {
                                    it
                                }
                            }
                            .toImmutableList()
                    )
                } else {
                    monsterItem
                }
            }.toImmutableList()
        )
    }

    fun addEffect(monsterId: Int, number: Int, effect: ActionUi): ScenarioLogicState {
        return this.copy(
            activeMonsters = activeMonsters.map { monsterItem ->
                if (monsterItem.id == monsterId) {
                    monsterItem.copy(
                        units = monsterItem
                            .units
                            .map {
                                if (it.number == number) {
                                    val newEffects = if (it.effects.contains(effect)) {
                                        it.effects - effect
                                    } else {
                                        it.effects + effect
                                    }
                                    it.copy(
                                        effects = newEffects.toImmutableList()
                                    )
                                } else {
                                    it
                                }
                            }
                            .toImmutableList()
                    )
                } else {
                    monsterItem
                }
            }.toImmutableList()
        )
    }

    private fun selectCard(monsterId: Int): MonsterCard {
        val monster = scenarioInfo.monsters.first { it.id == monsterId }
        return availableCards.filter { it.deckName == monster.deckName }.random()
    }

    private fun updateMonsterCard(monsterId: Int): ScenarioLogicState {
        val card = if (round != 0) {
            selectCard(monsterId)
        } else {
            null
        }
        val newAvaliableCards = if (card != null && !card.needsShuffle) {
            availableCards.filter { it.cardId != card.cardId }
        } else {
            availableCards
        }
        return this.copy(
            activeMonsters = activeMonsters.map {
                if (it.id == monsterId) {
                    it.copy(
                        currentCard = card?.let { MonsterAbilityCard.createFromMonsterCard(it) }
                    )
                } else {
                    it
                }
            }.toImmutableList(),
            availableCards = newAvaliableCards.toImmutableList()
        )
    }
}

data class ScenarioUIState(
    val name: String = "",
    val exp: Int = 0,
    val gold: Int = 0,
    val trapDamage: Int = 0,
    val round: Int = 0,
    val showMonsterDialog: Boolean = false,
    val monsters: List<MonsterItem> = emptyList(),
    val monstersForAdd: List<MonsterItem> = emptyList(),
    val magicChargeList: Map<Magic, MagicValue> = emptyMap()
)

enum class Magic(val icon: GameIcons) {
    FIRE(GameIcons.FIRE),
    FROST(GameIcons.FROST),
    AIR(GameIcons.AIR),
    EARTH(GameIcons.EARTH),
    SUN(GameIcons.SUN),
    MOON(GameIcons.MOON),
}

data class MagicValue(
    val value: Int = 0
) {
    fun decrease(): MagicValue {
        return copy(value = if (value > 0) value - 1 else 0)
    }

    fun update(): MagicValue {
        return copy(
            value = if (value == 0) {
                2
            } else {
                value - 1
            }
        )
    }

    fun getChargeImage(): Int? = when (value) {
        0 -> null
        1 -> R.drawable.ic_magic_half
        else -> R.drawable.ic_magic_full
    }

    fun color(magic: Magic): Color = when (value) {
        0 -> magic.icon.color.copy(alpha = 0.2f)
        1 -> magic.icon.color.copy(alpha = 0.5f)
        else -> magic.icon.color
    }
}

sealed interface ScenarioActions {
    data class AddUnits(val numbers: List<Int>, val monsterId: Int, val isElite: Boolean) :
        ScenarioActions

    data class RemoveUnit(val number: Int, val monsterId: Int) : ScenarioActions
    data object CompleteScenario : ScenarioActions
    data class AddMonster(val monsterIds: List<Int>) : ScenarioActions
    data class RemoveMonster(val monsterId: Int) : ScenarioActions
    data class UpdateUnitLife(val newValue: Int, val monsterId: Int, val unitNumber: Int) :
        ScenarioActions

    data object NextRound : ScenarioActions
    data class SwitchUnitEffect(val effect: ActionUi, val monsterId: Int, val unitNumber: Int) :
        ScenarioActions

    data object OpenMonstersDialog : ScenarioActions
    data object CloseMonstersDialog : ScenarioActions
    data class UpdateMagic(val magic: Magic) : ScenarioActions
}
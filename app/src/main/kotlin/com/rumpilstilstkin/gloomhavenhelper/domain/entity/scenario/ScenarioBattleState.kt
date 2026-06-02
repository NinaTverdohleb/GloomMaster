package com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStats
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.ScenarioStateMapper
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.ScenarioStateUi
import com.rumpilstilstkin.gloomhavenhelper.utils.mapIf
import kotlinx.collections.immutable.toImmutableList

data class ScenarioBattleState(
    val generalLevel: Int,
    val scenarioNumber: Int? = null,
    val name: String,
    val monsters: List<Monster>,
    val golds: Int,
    val exp: Int,
    val trapDamage: Int,
    val gamersCount: Int,
    val monsterLevel: Int,
    val deck: MonsterDeckState,
    val activeMonsters: List<MonsterItem> = emptyList(),
    val round: Int = 0,
    val magicState: MagicChargeState = MagicChargeState.initial(),
    val availableEffects: Set<MonsterStatType>
) {
    val monsterBySlug: Map<String, Monster> by lazy(LazyThreadSafetyMode.NONE) {
        monsters.associateBy { it.slug }
    }

    fun toUIState(): ScenarioStateUi = ScenarioStateMapper.toUiState(this)

    inline fun updateUnit(
        slug: String,
        number: Int,
        transform: (MonsterUnit) -> MonsterUnit,
    ): ScenarioBattleState {
        val newActive = activeMonsters.mapIf(
            predicate = { active -> active.slug == slug },
            transform = { active ->
                val newUnits = active.units.mapIf(
                    predicate = { unit -> unit.number == number },
                    transform = { unit ->
                        val transformed = transform(unit)
                        if (transformed == unit) unit else transformed
                    }
                )
                active.copy(units = newUnits.toImmutableList())
            }
        )
        return copy(activeMonsters = newActive.toImmutableList())
    }

    fun updateUnitStats(monsterSlug: String, number: Int, stats: MonsterStats): ScenarioBattleState =
        copy(
            activeMonsters = activeMonsters
                .updateMonster(monsterSlug) { monster ->
                    monster.updateUnit(number) { unit ->
                        unit.copy(
                            stats = stats.stats.toImmutableList(),
                            currentLife = stats.life,
                            maxLife = stats.life,
                            level = stats.level
                        )
                    }
                }
                .toImmutableList()
        )

    fun updateUnitLife(monsterSlug: String, number: Int, newValue: Int): ScenarioBattleState =
        copy(
            activeMonsters = activeMonsters
                .updateMonster(monsterSlug) { monster ->
                    monster.updateUnit(number) { it.copy(currentLife = newValue) }
                }
                .toImmutableList()
        )

    fun addEffect(monsterSlug: String, number: Int, effect: MonsterStatType): ScenarioBattleState =
        copy(
            activeMonsters = activeMonsters
                .updateMonster(monsterSlug) { monster ->
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

    private fun updateMonsterCard(monster: MonsterItem): ScenarioBattleState {
        if (round == 0) {
            return copy(
                activeMonsters = activeMonsters
                    .updateMonster(monster.slug) { m ->
                        m.copy(
                            units = m.units.map {
                                it.copy(isNew = false)
                            }.toImmutableList()
                        )
                    },
            )

        }
        val monster = monsters.first { it.slug == monster.slug }
        val drawResult = deck.drawCard(monster.deckName)

        return copy(
            activeMonsters = activeMonsters
                .updateMonster(monster.slug) { m ->
                    m.copy(
                        currentCard = drawResult.card,
                        units = m.units.map {
                            it.copy(isNew = false)
                        }.toImmutableList()
                    )
                },
            deck = drawResult.newState
        )
    }
}

package com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
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
}

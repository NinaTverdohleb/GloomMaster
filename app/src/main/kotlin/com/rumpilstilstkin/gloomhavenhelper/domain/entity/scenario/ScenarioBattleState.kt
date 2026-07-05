package com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType

data class ScenarioBattleState(
    val generalLevel: Int,
    val scenarioNumber: Int? = null,
    val name: String,
    val monsters: Map<String, Monster>,
    val golds: Int,
    val exp: Int,
    val trapDamage: Int,
    val gamersCount: Int,
    val monsterLevel: Int,
    val deck: MonsterDeckState,
    val activeMonsters: Map<String, MonsterItem> = emptyMap(),
    val round: Int = 0,
    val magicState: MagicChargeState = MagicChargeState.initial(),
    val availableEffects: Set<MonsterStatType>,
) {
    inline fun updateUnit(
        slug: String,
        number: Int,
        transform: MonsterUnit.() -> MonsterUnit,
    ): ScenarioBattleState {
        val newActive: Map<String, MonsterItem> =
            activeMonsters[slug]?.let { monster ->
                val newUnits =
                    monster
                        .units[number]
                        ?.transform()
                        ?.let { unit ->
                            mapOf(number to unit)
                        } ?: emptyMap()
                mapOf(slug to (monster.copy(units = monster.units + newUnits)))
            } ?: emptyMap()

        return copy(activeMonsters = activeMonsters + newActive)
    }
}

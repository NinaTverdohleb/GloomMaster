package com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.builders

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterUnit

class MonsterUnitBuilder(
    private val stateUnit: ScenarioGameStateMonsterUnit,
    private val baseMonster: Monster,
) {
    private var lastLevel: Int = 0
    private var newLevel: Int = 0
    private var gamersCount: Int = 1

    fun levels(levels: Pair<Int, Int>) =
        apply {
            this.lastLevel = levels.first
            this.newLevel = levels.second
        }

    fun gamersCount(count: Int) = apply { this.gamersCount = count }

    suspend fun build(
        availableEffects: Set<MonsterStatType>,
        getMonster: suspend (level: Int, slug: String) -> Monster?,
    ): MonsterUnit {
        val unitMonster =
            if (lastLevel == stateUnit.level) {
                baseMonster
            } else {
                val levelC = maxOf((newLevel + (stateUnit.level - lastLevel)), 0)
                getMonster(levelC, baseMonster.slug) ?: baseMonster
            }

        val maxMonsterLife =
            if (stateUnit.isElite) {
                unitMonster.eliteLife
            } else if (unitMonster.lifeMultiple) {
                unitMonster.life.times(gamersCount)
            } else {
                unitMonster.life
            }

        val newCurrentLife = maxMonsterLife - maxOf(stateUnit.maxLife - stateUnit.currentLife, 0)
        val stats = if (stateUnit.isElite) unitMonster.eliteStats else unitMonster.stats
        val unitEffects =
            (availableEffects - unitMonster.immunity).associateWith { effect ->
                stateUnit.effects.contains(effect)
            }
        return MonsterUnit(
            number = stateUnit.number,
            maxLife = maxMonsterLife,
            currentLife = newCurrentLife,
            stats = stats,
            isSpecial = stateUnit.isElite,
            level = unitMonster.level,
            effects = unitEffects,
            immunity = unitMonster.immunity,
            isNew = stateUnit.isNew,
            lifeMultiple = unitMonster.lifeMultiple,
        )
    }
}

package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.addUnits
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.updateMonster
import jakarta.inject.Inject
import kotlinx.collections.immutable.toImmutableList

class AddMonsterUnitsUseCase @Inject constructor() {
    operator fun invoke(
        state: ScenarioBattleState,
        slug: String,
        numbers: List<Int>,
        isElite: Boolean,
    ): ScenarioBattleState {
        val monster = state.monsters.first { it.slug == slug }
        val newUnits =
            numbers.map { number ->
                MonsterUnit.create(monster, number, isElite)
            }
        return state.copy(
            activeMonsters =
                state.activeMonsters
                    .updateMonster(slug) { it.addUnits(newUnits) }
                    .toImmutableList(),
        )
    }
}

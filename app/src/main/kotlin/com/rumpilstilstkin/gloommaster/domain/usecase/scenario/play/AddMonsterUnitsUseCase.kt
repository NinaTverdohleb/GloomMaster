package com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play

import com.rumpilstilstkin.gloommaster.domain.entity.scenario.MonsterUnit
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.ScenarioBattleState
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.addUnits
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.updateMonster
import jakarta.inject.Inject

class AddMonsterUnitsUseCase @Inject constructor() {
    operator fun invoke(
        state: ScenarioBattleState,
        slug: String,
        numbers: List<Int>,
        isElite: Boolean,
    ): ScenarioBattleState {
        val monster = state.monsters.getValue(slug)
        val effects = (state.availableEffects - monster.immunity).associateWith { false }
        val newUnits =
            numbers
                .map { number ->
                    MonsterUnit.create(
                        monster = monster,
                        number = number,
                        isElite = isElite,
                        gamersCount = state.gamersCount,
                        effects = effects,
                    )
                }.associateBy { it.number }
        return state.copy(
            activeMonsters =
                state.activeMonsters
                    .updateMonster(slug) {
                        addUnits(newUnits)
                    },
        )
    }
}

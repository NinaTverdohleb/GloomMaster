package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
import jakarta.inject.Inject

class RemoveUnitUseCase @Inject constructor() {
    operator fun invoke(
        state: ScenarioBattleState,
        slug: String,
        number: Int,
    ): ScenarioBattleState {
        val newActive =
            state.activeMonsters[slug]?.let { monster ->
                state.activeMonsters + (slug to monster.copy(units = monster.units - number))
            } ?: state.activeMonsters
        return state.copy(activeMonsters = newActive)
    }
}

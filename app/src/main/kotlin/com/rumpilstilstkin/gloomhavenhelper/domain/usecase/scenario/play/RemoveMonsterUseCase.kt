package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
import jakarta.inject.Inject

class RemoveMonsterUseCase @Inject constructor() {
    operator fun invoke(
        state: ScenarioBattleState,
        slug: String,
    ): ScenarioBattleState {
        val filtered = state.activeMonsters.filter { it.slug != slug }
        if (filtered.size == state.activeMonsters.size) return state
        return state.copy(activeMonsters = filtered)
    }
}

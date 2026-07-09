package com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play

import com.rumpilstilstkin.gloommaster.domain.entity.scenario.ScenarioBattleState
import jakarta.inject.Inject

class RemoveMonsterUseCase @Inject constructor() {
    operator fun invoke(
        state: ScenarioBattleState,
        slug: String,
    ): ScenarioBattleState {
        val filtered = state.activeMonsters - slug
        return state.copy(activeMonsters = filtered)
    }
}

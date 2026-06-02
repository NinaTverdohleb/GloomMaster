package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
import com.rumpilstilstkin.gloomhavenhelper.utils.mapIf
import jakarta.inject.Inject
import kotlinx.collections.immutable.toImmutableList

class RemoveUnitUseCase @Inject constructor() {
    operator fun invoke(
        state: ScenarioBattleState,
        slug: String,
        number: Int,
    ): ScenarioBattleState {
        val newActive = state.activeMonsters.mapIf(
            predicate = { active -> active.slug == slug },
            transform = { active ->
                val filtered = active.units.filter { it.number != number }
                if (filtered.size == active.units.size) active
                else active.copy(units = filtered.toImmutableList())
            }
        )
        return state.copy(activeMonsters = newActive)
    }
}
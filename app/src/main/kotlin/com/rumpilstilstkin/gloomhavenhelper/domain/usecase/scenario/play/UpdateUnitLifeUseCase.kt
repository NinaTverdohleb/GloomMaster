package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
import jakarta.inject.Inject

class UpdateUnitLifeUseCase @Inject constructor() {
    operator fun invoke(
        state: ScenarioBattleState,
        slug: String,
        number: Int,
        newLife: Int,
    ): ScenarioBattleState =
        state.updateUnit(slug, number) {
            copy(currentLife = newLife.coerceIn(0, maxLife))
        }
}

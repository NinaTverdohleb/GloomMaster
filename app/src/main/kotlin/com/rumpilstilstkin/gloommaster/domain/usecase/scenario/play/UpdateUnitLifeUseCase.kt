package com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play

import com.rumpilstilstkin.gloommaster.domain.entity.scenario.ScenarioBattleState
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

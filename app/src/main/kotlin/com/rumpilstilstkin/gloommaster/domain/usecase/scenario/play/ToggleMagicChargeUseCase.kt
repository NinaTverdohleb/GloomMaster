package com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play

import com.rumpilstilstkin.gloommaster.domain.entity.Magic
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.ScenarioBattleState
import jakarta.inject.Inject

class ToggleMagicChargeUseCase @Inject constructor() {
    operator fun invoke(
        state: ScenarioBattleState,
        magic: Magic,
    ): ScenarioBattleState = state.copy(magicState = state.magicState.toggle(magic))
}

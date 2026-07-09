package com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play

import com.rumpilstilstkin.gloommaster.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.ScenarioBattleState
import jakarta.inject.Inject

class ToggleUnitEffectUseCase @Inject constructor() {
    operator fun invoke(
        state: ScenarioBattleState,
        slug: String,
        number: Int,
        effect: MonsterStatType,
    ): ScenarioBattleState =
        state.updateUnit(slug, number) {
            val newEffects = effects + (effect to !effects.getValue(effect))
            copy(effects = newEffects)
        }
}

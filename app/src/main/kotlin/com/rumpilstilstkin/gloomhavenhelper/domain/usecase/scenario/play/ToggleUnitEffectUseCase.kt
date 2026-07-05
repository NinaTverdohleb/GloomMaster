package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
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

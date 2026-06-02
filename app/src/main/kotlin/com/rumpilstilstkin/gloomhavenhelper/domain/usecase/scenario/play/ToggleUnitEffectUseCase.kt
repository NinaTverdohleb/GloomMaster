package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
import jakarta.inject.Inject
import kotlinx.collections.immutable.toImmutableList

class ToggleUnitEffectUseCase @Inject constructor() {
    operator fun invoke(
        state: ScenarioBattleState,
        slug: String,
        number: Int,
        effect: MonsterStatType,
    ): ScenarioBattleState =
        state.updateUnit(slug, number) {
            val newEffects =
                if (effect in it.effects) it.effects - effect else it.effects + effect
            it.copy(effects = newEffects.toImmutableList())
        }
}

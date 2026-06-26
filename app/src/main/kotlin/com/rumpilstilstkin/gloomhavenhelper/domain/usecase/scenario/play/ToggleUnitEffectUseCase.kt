package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
import jakarta.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet

class ToggleUnitEffectUseCase @Inject constructor() {
    operator fun invoke(
        state: ScenarioBattleState,
        slug: String,
        number: Int,
        effect: MonsterStatType,
    ): ScenarioBattleState =
        state.updateUnit(slug, number) {
            val newEffects =
                if (effect in effects) effects - effect else effects + effect
            copy(effects = newEffects.toImmutableSet())
        }
}

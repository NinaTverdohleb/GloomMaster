package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.GetMonsterStatsForLevelUseCase
import jakarta.inject.Inject

class UpdateUnitLevelUseCase @Inject constructor(
    private val getMonsterStatsForLevelUseCase: GetMonsterStatsForLevelUseCase,
) {
    suspend operator fun invoke(
        state: ScenarioBattleState,
        slug: String,
        number: Int,
        level: Int,
        isElite: Boolean,
    ): ScenarioBattleState {
        val stats =
            getMonsterStatsForLevelUseCase(monsterSlug = slug, level = level, isElite = isElite)
        return state.updateUnit(slug, number) {
            it.copy(
                currentLife = stats.life,
                level = stats.level,
            )
        }
    }
}

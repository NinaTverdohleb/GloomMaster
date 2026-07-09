package com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play

import com.rumpilstilstkin.gloommaster.domain.entity.scenario.ScenarioBattleState
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.GetMonsterStatsForLevelUseCase
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
            val maxMonsterLife =
                if (lifeMultiple) {
                    stats.life.times(state.gamersCount)
                } else {
                    stats.life
                }
            val newCurrentLife = maxMonsterLife - maxOf(maxLife - currentLife, 0)
            copy(
                currentLife = newCurrentLife,
                level = stats.level,
                maxLife = maxMonsterLife,
            )
        }
    }
}

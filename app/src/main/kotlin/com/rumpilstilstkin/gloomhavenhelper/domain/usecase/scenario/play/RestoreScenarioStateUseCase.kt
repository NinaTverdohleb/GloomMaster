package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play

import com.rumpilstilstkin.gloomhavenhelper.data.MonsterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioGameStateRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.LevelInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.builders.buildScenarioBattleState
import javax.inject.Inject

class RestoreScenarioStateUseCase @Inject constructor(
    private val scenarioGameStateRepository: ScenarioGameStateRepository,
    private val monsterRepository: MonsterRepository,
    private val scenarioRepository: ScenarioRepository,
) {
    suspend operator fun invoke(
        team: TeamInfo,
        levelInfo: LevelInfo?,
        locale: String,
    ): ScenarioBattleState {
        val state = scenarioGameStateRepository.get()
        return buildScenarioBattleState {
            levelInfo?.let { levelInfo(it) }
            team(team)

            state?.let { gameState ->
                val scenarioName =
                    gameState.scenarioNumber?.let {
                        scenarioRepository.getScenario(it, locale).scenarioName
                    } ?: ""
                name(scenarioName)

                val monsterBySlug =
                    monsterRepository
                        .getMonstersBySlugs(gameState.monsterSlugs, team.level, locale)
                        .associateBy { it.slug }

                monsters(monsterBySlug)
                gameState(gameState)

                getAdditionalMonster { level, slug ->
                    monsterRepository
                        .getMonstersBySlugs(
                            slugs = listOf(slug),
                            level = level,
                            locale = locale,
                        ).firstOrNull()
                }
            }
        }
    }
}

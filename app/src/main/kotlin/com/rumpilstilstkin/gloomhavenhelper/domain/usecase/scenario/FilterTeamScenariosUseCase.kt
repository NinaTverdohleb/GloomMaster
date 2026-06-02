package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

import com.rumpilstilstkin.gloomhavenhelper.data.AchievementRepository
import com.rumpilstilstkin.gloomhavenhelper.data.LocaleRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Achievement
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioShortInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamScenarios
import javax.inject.Inject

class FilterTeamScenariosUseCase @Inject constructor(
    private val localeRepository: LocaleRepository,
    private val scenarioRepository: ScenarioRepository,
    private val achievementRepository: AchievementRepository,
) {
    suspend operator fun invoke(
        achievements: List<Achievement>,
        scenario: List<ScenarioShortInfo>,
    ): TeamScenarios {
        val avaliableScenarios =
            scenarioRepository.getScenariosWithName(
                scenarios = scenario,
                locale = localeRepository.getCurrentLocale(),
            )
        val dictionary = achievementRepository.currentDictionary()
        val allAchievement =
            (achievements)
                .mapNotNull { dictionary[it.slug]?.trim() }
                .filter { it.isNotEmpty() }
                .toSet()
        val completed = avaliableScenarios.filter { it.isCompleted }
        val avaliable =
            avaliableScenarios
                .filter { !it.isCompleted }
                .toSet()
        val (active, blocked) =
            avaliable.partition { scenario ->
                scenario.scenarioRequirements.evaluate(allAchievement)
            }
        return TeamScenarios(
            activeScenarios = active,
            blockedScenarios = blocked,
            completedScenarios = completed,
        )
    }
}

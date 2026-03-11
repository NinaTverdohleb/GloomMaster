package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

import com.rumpilstilstkin.gloomhavenhelper.data.AchievementRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioGameStateRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Achievement
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CompleteScenarioUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val scenarioRepository: ScenarioRepository,
    private val achievementRepository: AchievementRepository,
    private val scenarioGameStateRepository: ScenarioGameStateRepository
) {
    suspend operator fun invoke(scenarioNumber: Int) {
        teamRepository.currentTeam.first()?.let { team ->

            val oldScenarios = scenarioRepository.getAllTeamScenarios(team.teamId)
            scenarioRepository.getScenario(scenarioNumber).let { scenario ->

                val newScenario =
                    scenario.newScenario.map { scenarioRepository.getScenario(it) }
                        .filter { newScenario ->
                            oldScenarios.none { oldScenario -> oldScenario.scenarioNumber == newScenario.scenarioNumber }
                                    && newScenario.pack in team.packs.toSet()
                        }
                scenarioRepository.completeTeamScenario(team.teamId, scenarioNumber)
                scenarioGameStateRepository.delete()
                newScenario.forEach {
                    scenarioRepository.saveTeamScenario(it, team.teamId)
                }

                val newTeamAchievements = updateAchievements(
                    teamAchievements = team.teamAchievement,
                    scenarioAchievements = scenario.teamAchievements,
                    getAllAchievement = { achievementRepository.getTeamAchievements() }
                )
                val newGlobalAchievement = updateAchievements(
                    teamAchievements = team.globalAchievement,
                    scenarioAchievements = scenario.globalAchievements,
                    getAllAchievement = { achievementRepository.getGlobalAchievements() }
                )
                teamRepository.updateTeam(
                    team.copy(
                        teamAchievement = newTeamAchievements,
                        globalAchievement = newGlobalAchievement,
                    )
                )
            }
        }
    }

    private suspend fun updateAchievements(
        teamAchievements: List<Achievement>,
        scenarioAchievements: List<String>,
        getAllAchievement: suspend () -> List<Achievement>
    ): List<Achievement> {
        val allAchievement = getAllAchievement()
        val newAchievement = teamAchievements.associateBy { it.name }.toMutableMap()
        scenarioAchievements.forEach { achievement ->
            allAchievement.find { it.name == achievement }?.also { globalAchievement ->
                val existingItem = newAchievement[achievement]
                if (globalAchievement.value > 1) {
                    if (existingItem != null) {
                        val newValue =
                            (existingItem.value + 1).coerceAtMost(globalAchievement.maxValue)
                        newAchievement[achievement] = existingItem.copy(value = newValue)
                    } else {
                        newAchievement[achievement] = globalAchievement
                    }
                } else {
                    if (existingItem == null) {
                        newAchievement[achievement] = globalAchievement
                    }
                }
            }

        }
        return newAchievement.values.toList()
    }
}
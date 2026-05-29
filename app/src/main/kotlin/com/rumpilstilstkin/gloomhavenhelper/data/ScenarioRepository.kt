package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.ScenarioDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TeamScenarioDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamScenarioBd
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toDomain
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toInfoDomain
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toShortDomain
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.LogicalCondition
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioInfoWithName
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioShortInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScenarioRepository @Inject constructor(
    private val scenarioDao: ScenarioDao,
    private val teamScenarioDao: TeamScenarioDao,
    private val achievementRepository: AchievementRepository
) {
    suspend fun getAllScenarios(
        locale: String
    ): List<ScenarioInfoWithName> = scenarioDao.getAll(
        targetLocale = locale,
        defaultLocale = LocaleRepository.DEFAULT_LOCALE
    ).map {
        it.toDomain(
            isCompleted = false,
            dictionary = achievementRepository.dictionary.first()
        )
    }

    suspend fun getAllTeamScenarios(teamId: Int): List<ScenarioShortInfo> =
        teamScenarioDao.getTeamScenarios(teamId).map {
            it.scenario.toShortDomain(
                isCompleted = it.teamScenario.completed
            )
        }

    fun getTeamScenariosFlow(teamId: Int): Flow<List<ScenarioShortInfo>> =
        teamScenarioDao.getTeamScenariosFlow(teamId)
            .map { scenarioBds ->
                scenarioBds.map {
                    it.scenario.toShortDomain(
                        isCompleted = it.teamScenario.completed
                    )
                }
            }

    suspend fun getScenario(
        scenarioNumber: Int,
        locale: String,
        isCompleted: Boolean = false
    ): ScenarioInfoWithName =
        scenarioDao.getScenarioWithName(
            scenarioNumber = scenarioNumber,
            targetLocale = locale,
            defaultLocale = LocaleRepository.DEFAULT_LOCALE
        )
            .toDomain(
                isCompleted = isCompleted,
                dictionary = achievementRepository.dictionary.first()
            )

    suspend fun getShortScenario(scenarioNumber: Int): ScenarioInfo =
        scenarioDao.getScenario(
            scenarioNumber = scenarioNumber,
        ).toInfoDomain()

    suspend fun saveTeamScenario(scenarioNumber: Int, teamId: Int) {
        teamScenarioDao.insertAll(
            TeamScenarioBd(
                teamId = teamId,
                scenarioNumber = scenarioNumber,
            )
        )
    }

    suspend fun addTeamScenarios(scenarios: List<Pair<Int, Boolean>>, teamId: Int) {
        teamScenarioDao.insertAll(
            *scenarios.map { (number, completed) ->
                TeamScenarioBd(
                    teamId = teamId,
                    scenarioNumber = number,
                    completed = completed
                )
            }.toTypedArray()
        )
    }

    suspend fun completeTeamScenario(teamId: Int, scenarioNumber: Int) {
        teamScenarioDao.getTeamScenarioClear(teamId, scenarioNumber).let {
            teamScenarioDao.update(it.copy(completed = true))
        }
    }

    suspend fun restoreTeamScenario(teamId: Int, scenarioNumber: Int) {
        teamScenarioDao.getTeamScenarioClear(teamId, scenarioNumber).let {
            teamScenarioDao.update(it.copy(completed = false))
        }
    }

    suspend fun deleteTeamScenario(teamId: Int, scenarioNumber: Int) {
        teamScenarioDao.deleteTeamScenario(teamId, scenarioNumber)

    }
}
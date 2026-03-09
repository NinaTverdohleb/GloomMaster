package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.ScenarioDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TeamScenarioDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamScenarioBd
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toDomain
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioShortInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScenarioRepository @Inject constructor(
    private val scenarioDao: ScenarioDao,
    private val teamScenarioDao: TeamScenarioDao
) {
    suspend fun getAllScenarios(): List<ScenarioInfo> = scenarioDao.getAll().map { it.toDomain() }

    suspend fun getAllTeamScenarios(teamId: Int): List<ScenarioShortInfo> =
        teamScenarioDao.getTeamScenarios(teamId).map { it.toDomain() }

    suspend fun getTeamScenariosFlow(teamId: Int): Flow<List<ScenarioShortInfo>> =
        teamScenarioDao.getTeamScenariosFlow(teamId)
            .map { scenarioBds -> scenarioBds.map { it.toDomain() } }

    suspend fun getScenario(scenarioNumber: Int): ScenarioInfo =
        scenarioDao.getScenario(scenarioNumber).toDomain()

    suspend fun saveTeamScenario(scenario: ScenarioInfo, teamId: Int) {
        teamScenarioDao.insertAll(
            TeamScenarioBd(
                teamId = teamId,
                scenarioNumber = scenario.scenarioNumber,
                scenarioName = scenario.scenarioName,
                scenarioRequirements = scenario.scenarioRequirements.condition,
                location = scenario.location,
                pack = scenario.pack.name
            )
        )
    }

    suspend fun completeTeamScenario(teamId: Int, scenarioId: Int) {
        teamScenarioDao.getTeamScenario(teamId, scenarioId).let {
            teamScenarioDao.update(it.copy(completed = true))
        }
    }
}
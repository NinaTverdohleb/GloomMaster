package com.rumpilstilstkin.gloomhavenhelper.data

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.ScenarioDao
import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TeamScenarioDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamScenarioBd
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.localized
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toDomain
import com.rumpilstilstkin.gloomhavenhelper.data.mappers.toShortDomain
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioShortInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.export.TeamScenarioDataDto
import com.rumpilstilstkin.gloomhavenhelper.localization.LocaleSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScenarioRepository @Inject constructor(
    private val scenarioDao: ScenarioDao,
    private val teamScenarioDao: TeamScenarioDao,
    private val translationRepository: TranslationRepository,
    private val localeSource: LocaleSource,
) {
    suspend fun getAllScenarios(): List<ScenarioInfo> {
        val resolver = translationRepository.resolver(localeSource.current)
        return scenarioDao.getAll().map { it.toDomain().localized(resolver) }
    }

    suspend fun getAllTeamScenarios(teamId: Int): List<ScenarioShortInfo> {
        val resolver = translationRepository.resolver(localeSource.current)
        return teamScenarioDao.getTeamScenarios(teamId).map {
            it.scenario.toShortDomain(isCompleted = it.teamScenario.completed).localized(resolver)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getTeamScenariosFlow(teamId: Int): Flow<List<ScenarioShortInfo>> =
        teamScenarioDao.getTeamScenariosFlow(teamId)
            .combine(resolverFlow()) { scenarioBds, resolver ->
                scenarioBds.map {
                    it.scenario.toShortDomain(isCompleted = it.teamScenario.completed)
                        .localized(resolver)
                }
            }

    /**
     * Active-locale resolver as a stream: switches when the language changes and re-emits when
     * the translation store is (re)seeded, so localized text refreshes without a restart.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun resolverFlow(): Flow<TextResolver> =
        localeSource.locale.flatMapLatest { translationRepository.resolverFlow(it) }

    suspend fun getScenario(scenarioNumber: Int): ScenarioInfo {
        val resolver = translationRepository.resolver(localeSource.current)
        return scenarioDao.getScenario(scenarioNumber).toDomain().localized(resolver)
    }

    suspend fun saveTeamScenario(scenario: ScenarioInfo, teamId: Int) {
        teamScenarioDao.insertAll(
            TeamScenarioBd(
                teamId = teamId,
                scenarioNumber = scenario.scenarioNumber,
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
package com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario

import android.util.Log
import com.rumpilstilstkin.gloomhavenhelper.data.LocaleRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ScenarioRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioInfoWithName
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAvailableScenariosForTeamUseCase @Inject constructor(
    private val teamRepository: TeamRepository,
    private val scenarioRepository: ScenarioRepository,
    private val localeRepository: LocaleRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<ScenarioInfoWithName>> =
        teamRepository.currentTeam
            .combine(
                localeRepository.observeLocale
            ) { team, locale -> team to locale }
            .flatMapLatest { (team, locale) ->
                if (team == null) {
                    flowOf(emptyList())
                } else {
                    val allScenarios =
                        scenarioRepository.getAllScenarios(locale, team.packs.map { it.name })
                    scenarioRepository.getTeamScenariosFlow(team.teamId).map { scenarios ->
                        val teamScenarioNumbers = scenarios.map { it.scenarioNumber }.toSet()
                        allScenarios.filter { scenario ->
                            scenario.scenarioNumber !in teamScenarioNumbers
                        }
                    }
                }
            }
}

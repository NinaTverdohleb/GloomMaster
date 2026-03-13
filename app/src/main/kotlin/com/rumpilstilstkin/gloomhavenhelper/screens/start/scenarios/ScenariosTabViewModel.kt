package com.rumpilstilstkin.gloomhavenhelper.screens.start.scenarios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.CompleteScenarioUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.CreateActiveScenarioUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.DeleteScenarioUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.GetTeamScenariosUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.RestoreScenarioUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreens
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreens.Scenario
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent.Screen
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScenariosTabViewModel @Inject constructor(
    getTeamScenariosUseCase: GetTeamScenariosUseCase,
    private val completeScenarioUseCase: CompleteScenarioUseCase,
    private val createActiveScenarioUseCase: CreateActiveScenarioUseCase,
    private val restoreScenarioUseCase: RestoreScenarioUseCase,
    private val deleteScenarioUseCase: DeleteScenarioUseCase,
) : ViewModel() {

    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    private val sectionsExpanded = MutableStateFlow(
        mapOf(
            ScenarioSectionType.ACCESS to true,
            ScenarioSectionType.BLOCKED to false,
            ScenarioSectionType.FINISHED to false,
        )
    )

    val uiState: StateFlow<ScenariosTabStateUi> = combine(
        getTeamScenariosUseCase(),
        sectionsExpanded,
    ) { teamScenarios, expandedMap ->
        val sections = buildMap {
            if (teamScenarios.activeScenarios.isNotEmpty()) {
                put(
                    ScenarioSectionType.ACCESS,
                    ScenariosSection(
                        scenarios = teamScenarios.activeScenarios.map { it.toUi() }
                            .toImmutableList(),
                        isExpanded = expandedMap[ScenarioSectionType.ACCESS] ?: true,
                    )
                )
            }
            if (teamScenarios.blockedScenarios.isNotEmpty()) {
                put(
                    ScenarioSectionType.BLOCKED,
                    ScenariosSection(
                        scenarios = teamScenarios.blockedScenarios.map { it.toUi() }
                            .toImmutableList(),
                        isExpanded = expandedMap[ScenarioSectionType.BLOCKED] ?: false,
                    )
                )
            }
            if (teamScenarios.completedScenarios.isNotEmpty()) {
                put(
                    ScenarioSectionType.FINISHED,
                    ScenariosSection(
                        scenarios = teamScenarios.completedScenarios.map { it.toUi() }
                            .toImmutableList(),
                        isExpanded = expandedMap[ScenarioSectionType.FINISHED] ?: false,
                    )
                )
            }
        }.toImmutableMap()

        ScenariosTabStateUi(sections = sections)
    }.stateIn(
        scope = viewModelScope,
        initialValue = ScenariosTabStateUi(),
        started = SharingStarted.WhileSubscribed(5000),
    )

    fun onAction(action: ScenariosTabAction) = viewModelScope.launch {
        when (action) {
            is ScenariosTabAction.ToggleSection -> {
                sectionsExpanded.update { current ->
                    current.toMutableMap().apply {
                        this[action.sectionType] = !(this[action.sectionType] ?: false)
                    }
                }
            }

            is ScenariosTabAction.StartScenario -> {
                createActiveScenarioUseCase(action.scenarioId).onSuccess {
                    _navigationEvents.emit(Screen(Scenario))
                }
            }

            is ScenariosTabAction.CompleteScenario ->
                completeScenarioUseCase.invoke(scenarioNumber = action.scenarioId)

            ScenariosTabAction.AddScenario ->
                _navigationEvents.emit(Screen(GlHelperScreens.AddScenarioForTeam))

            is ScenariosTabAction.DeleteScenario -> {
                deleteScenarioUseCase.invoke(action.scenarioNumber)
            }
            is ScenariosTabAction.RestoreScenario -> {
                restoreScenarioUseCase.invoke(action.scenarioNumber)
            }
        }
    }
}

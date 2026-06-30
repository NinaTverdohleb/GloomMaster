package com.rumpilstilstkin.gloomhavenhelper.screens.start.scenarios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.CreateActiveScenarioUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.GetTeamScenariosUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreen
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreen.Scenario
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent.Screen
import com.rumpilstilstkin.gloomhavenhelper.screens.core.ScreenEffect
import com.rumpilstilstkin.gloomhavenhelper.screens.core.createOverlaySession
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toUi
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.dialog.delete.DeleteScenarioDialogContract
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.dialog.menu.MenuScenarioDialogContract
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.dialog.menu.MenuScenarioResult
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
    private val createActiveScenarioUseCase: CreateActiveScenarioUseCase,
) : ViewModel() {
    private val _screenEvents = MutableSharedFlow<ScreenEffect>()
    val screenEvents = _screenEvents.asSharedFlow()

    private val sectionsExpanded =
        MutableStateFlow(
            mapOf(
                ScenarioSectionType.ACCESS to true,
                ScenarioSectionType.BLOCKED to true,
                ScenarioSectionType.FINISHED to true,
            ),
        )

    val uiState: StateFlow<ScenariosTabStateUi> =
        combine(
            getTeamScenariosUseCase(),
            sectionsExpanded,
        ) { teamScenarios, expandedMap ->
            val sections =
                buildMap {
                    if (teamScenarios.activeScenarios.isNotEmpty()) {
                        put(
                            ScenarioSectionType.ACCESS,
                            ScenariosSection(
                                scenarios =
                                    teamScenarios.activeScenarios
                                        .map { it.toUi() }
                                        .toImmutableList(),
                                isExpanded = expandedMap[ScenarioSectionType.ACCESS] ?: true,
                            ),
                        )
                    }
                    if (teamScenarios.blockedScenarios.isNotEmpty()) {
                        put(
                            ScenarioSectionType.BLOCKED,
                            ScenariosSection(
                                scenarios =
                                    teamScenarios.blockedScenarios
                                        .map { it.toUi(false) }
                                        .toImmutableList(),
                                isExpanded = expandedMap[ScenarioSectionType.BLOCKED] ?: false,
                            ),
                        )
                    }
                    if (teamScenarios.completedScenarios.isNotEmpty()) {
                        put(
                            ScenarioSectionType.FINISHED,
                            ScenariosSection(
                                scenarios =
                                    teamScenarios.completedScenarios
                                        .map { it.toUi() }
                                        .toImmutableList(),
                                isExpanded = expandedMap[ScenarioSectionType.FINISHED] ?: false,
                            ),
                        )
                    }
                }.toImmutableMap()

            ScenariosTabStateUi(sections = sections)
        }.stateIn(
            scope = viewModelScope,
            initialValue = ScenariosTabStateUi(),
            started = SharingStarted.WhileSubscribed(5000),
        )

    fun onAction(action: ScenariosTabAction) =
        viewModelScope.launch {
            when (action) {
                is ScenariosTabAction.ToggleSection -> {
                    sectionsExpanded.update { current ->
                        current.toMutableMap().apply {
                            this[action.sectionType] = !(this[action.sectionType] ?: false)
                        }
                    }
                }

                ScenariosTabAction.AddScenario -> {
                    _screenEvents.emit(ScreenEffect.Navigation(Screen(GlHelperScreen.AddScenarioForTeam)))
                }

                is ScenariosTabAction.SelectScenario -> {
                    val session =
                        createOverlaySession(
                            contract = MenuScenarioDialogContract,
                            input = action.scenario,
                            onResult = { result ->
                                when (result) {
                                    is MenuScenarioResult.DeleteScenarioRequest -> {
                                        openDeleteScenarioDialog(result.scenario)
                                    }

                                    is MenuScenarioResult.PlayScenario -> {
                                        viewModelScope.launch {
                                            createActiveScenarioUseCase(action.scenario.scenarioNumber)
                                                .onSuccess {
                                                    _screenEvents.emit(
                                                        ScreenEffect.Navigation(
                                                            (Screen(Scenario)),
                                                        ),
                                                    )
                                                }
                                        }
                                    }

                                    else -> {}
                                }
                            },
                        )
                    _screenEvents.emit(ScreenEffect.OpenBottomSheet(session))
                }
            }
        }

    private fun openDeleteScenarioDialog(scenario: ShortScenarioUI) {
        viewModelScope.launch {
            val session =
                createOverlaySession(
                    contract = DeleteScenarioDialogContract,
                    input = scenario,
                    onResult = { },
                )
            _screenEvents.emit(ScreenEffect.OpenDialog(session))
        }
    }
}

package com.rumpilstilstkin.gloomhavenhelper.screens.teem.scenarios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.AddScenarioToTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.GetAvailableScenariosForTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
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
class AddScenarioForTeamViewModel @Inject constructor(
    getAvailableScenariosUseCase: GetAvailableScenariosForTeamUseCase,
    private val addScenarioToTeamUseCase: AddScenarioToTeamUseCase,
) : ViewModel() {
    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    private val logicState = MutableStateFlow(AddScenarioForTeamLogicState())

    val uiState: StateFlow<AddScenarioForTeamUiState> =
        combine(
            logicState,
            getAvailableScenariosUseCase(),
        ) { state, allScenarios ->
            val filteredScenarios: List<ShortScenarioUI> =
                allScenarios
                    .filter { scenario ->
                        if (state.searchText.isBlank()) {
                            true
                        } else {
                            scenario.scenarioNumber.toString().contains(state.searchText, ignoreCase = true) ||
                                scenario.scenarioName.contains(state.searchText, ignoreCase = true)
                        }
                    }.map { it.toUi() }
                    .sortedBy { it.scenarioNumber }

            AddScenarioForTeamUiState(
                scenarios = filteredScenarios.toImmutableList(),
                searchText = state.searchText,
                selectedScenario = state.selectedScenario,
            )
        }.stateIn(
            scope = viewModelScope,
            initialValue = AddScenarioForTeamUiState(),
            started = SharingStarted.WhileSubscribed(5000),
        )

    fun onAction(action: AddScenarioForTeamAction) {
        viewModelScope.launch {
            when (action) {
                is AddScenarioForTeamAction.SearchTextChange -> {
                    logicState.update { it.copy(searchText = action.text) }
                }

                is AddScenarioForTeamAction.SelectScenario -> {
                    logicState.update { it.copy(selectedScenario = action.scenario) }
                }

                is AddScenarioForTeamAction.DismissDialog -> {
                    logicState.update { it.copy(selectedScenario = null) }
                }

                is AddScenarioForTeamAction.ConfirmAddScenario -> {
                    logicState.value.selectedScenario?.let { scenario ->
                        addScenarioToTeamUseCase(scenario.scenarioNumber)
                        logicState.update { it.copy(selectedScenario = null) }
                    }
                }

                is AddScenarioForTeamAction.Back -> {
                    _navigationEvents.emit(GlHelperEvent.Back)
                }
            }
        }
    }
}

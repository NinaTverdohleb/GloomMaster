package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.GetAvailableScenariosForTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent.Back
import com.rumpilstilstkin.gloomhavenhelper.screens.core.ScreenEffect
import com.rumpilstilstkin.gloomhavenhelper.screens.core.createOverlaySession
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toUi
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.dialog.add.AddScenarioDialogContract
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
) : ViewModel() {
    private val _screenEvents = MutableSharedFlow<ScreenEffect>()
    val screenEvents = _screenEvents.asSharedFlow()

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
                    val session =
                        createOverlaySession(
                            contract = AddScenarioDialogContract,
                            input = action.scenario,
                            onResult = {
                                ScreenEffect.Message(R.string.scenario_added)
                            },
                        )
                    _screenEvents.emit(ScreenEffect.OpenBottomSheet(session))
                }

                is AddScenarioForTeamAction.Back -> {
                    _screenEvents.emit(ScreenEffect.Navigation(Back))
                }
            }
        }
    }
}

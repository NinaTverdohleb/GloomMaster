package com.rumpilstilstkin.gloommaster.screens.scenario.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.GetAvailableScenariosForTeamUseCase
import com.rumpilstilstkin.gloommaster.navigation.events.GlHelperEvent.Back
import com.rumpilstilstkin.gloommaster.screens.core.ScreenEffect
import com.rumpilstilstkin.gloommaster.screens.core.createOverlaySession
import com.rumpilstilstkin.gloommaster.screens.models.ShortScenarioUI
import com.rumpilstilstkin.gloommaster.screens.models.toUi
import com.rumpilstilstkin.gloommaster.screens.scenario.dialog.add.AddScenarioDialogContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddScenarioForTeamViewModel @Inject constructor(
    getAvailableScenariosUseCase: GetAvailableScenariosForTeamUseCase,
) : ViewModel() {
    private val _screenEvents = Channel<ScreenEffect>(Channel.BUFFERED)
    val screenEvents = _screenEvents.receiveAsFlow()

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
                    _screenEvents.send(ScreenEffect.OpenBottomSheet(session))
                }

                is AddScenarioForTeamAction.Back -> {
                    _screenEvents.send(ScreenEffect.Navigation(Back))
                }
            }
        }
    }
}

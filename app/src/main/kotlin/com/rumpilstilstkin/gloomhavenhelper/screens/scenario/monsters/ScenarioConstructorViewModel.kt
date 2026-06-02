package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.monsters

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.AddMonstersForCurrentScenarioUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.GetAvailableMonstersForTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScenarioConstructorViewModel @Inject constructor(
    private val getAvailableMonstersForTeamUseCase: GetAvailableMonstersForTeamUseCase,
    private val addMonstersForCurrentScenarioUseCase: AddMonstersForCurrentScenarioUseCase
) : ViewModel() {

    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    private val logicState: MutableStateFlow<ScenarioConstructorStateLogic> =
        MutableStateFlow(ScenarioConstructorStateLogic())

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<ScenarioConstructorStateUi> = logicState
        .filterNotNull()
        .map { state ->
            if (state.allMonsters.isEmpty()) {
                ScenarioConstructorStateUi.Loading
            } else {
                ScenarioConstructorStateUi.Content(
                    availableMonsters = (state.allMonsters - state.selectedMonsters.keys).map { entry ->
                        MonsterNameUi(
                            entry.key,
                            entry.value
                        )
                    }.toImmutableList(),
                    selectedMonsters = state.selectedMonsters.map { entry ->
                        MonsterNameUi(
                            entry.key,
                            entry.value
                        )
                    }.toImmutableList(),
                )
            }

        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ScenarioConstructorStateUi.Loading
        )

    init {
        viewModelScope.launch {
            val allMonsters = getAvailableMonstersForTeamUseCase()
            logicState.update {
                it.copy(
                    allMonsters = allMonsters,
                )
            }
        }
    }

    fun onAction(action: ScenarioConstructorAction) {
        viewModelScope.launch {
            when (action) {
                is ScenarioConstructorAction.Back -> {
                    _navigationEvents.emit(GlHelperEvent.Back)
                }

                is ScenarioConstructorAction.ToggleMonster -> {
                    logicState.update { state ->
                        val newSelected: Map<String, String> =
                            if (action.monster.slug in state.selectedMonsters.keys) {
                                state.selectedMonsters - action.monster.slug
                            } else {
                                state.selectedMonsters + (action.monster.slug to action.monster.name)
                            }
                        state.copy(selectedMonsters = newSelected)
                    }
                }

                is ScenarioConstructorAction.AddMonsters -> {
                    async {
                        addMonstersForCurrentScenarioUseCase(
                            logicState.value.selectedMonsters.keys.toList()
                        )
                    }.await()
                    _navigationEvents.emit(GlHelperEvent.Back)
                }
            }
        }
    }
}

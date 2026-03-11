package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.CompleteScenarioUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.GetMonsterStatsForLevelUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.GetScenarioInfoUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.SaveScenarioStateUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.ScenarioActions
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.ScenarioLogicState
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.ScenarioStateMapper
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.ScenarioStateUi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(FlowPreview::class)
@HiltViewModel(assistedFactory = ScenarioViewModel.Factory::class)
class ScenarioViewModel @AssistedInject constructor(
    private val getScenarioInfoUseCase: GetScenarioInfoUseCase,
    private val completeScenarioUseCase: CompleteScenarioUseCase,
    private val getMonsterStatsForLevelUseCase: GetMonsterStatsForLevelUseCase,
    private val saveScenarioStateUseCase: SaveScenarioStateUseCase,
    @Assisted private val scenarioNumber: Int?,
    @Assisted private val restore: Boolean,
) : ViewModel() {
    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    private val logicState = MutableStateFlow<ScenarioLogicState?>(null)
    val uiState: StateFlow<ScenarioStateUi> = logicState
        .filterNotNull()
        .map { it.toUIState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ScenarioStateUi()
        )

    init {
        loadScenario()
        logicState
            .filterNotNull()
            .debounce(500)
            .map { logicState ->
                ScenarioStateMapper.stateForSave(logicState)
            }
            .distinctUntilChanged()
            .onEach { newState ->
                saveScenarioStateUseCase(newState)
            }
            .launchIn(viewModelScope)
    }

    private fun loadScenario() {
        viewModelScope.launch {
            getScenarioInfoUseCase(scenarioNumber, restore).onSuccess { battleInfo ->
                logicState.update {
                    ScenarioLogicState.restore(battleInfo)
                }
            }
        }
    }

    fun onAction(action: ScenarioActions) {
        viewModelScope.launch {
            when (action) {
                is ScenarioActions.AddMonster -> updateState {
                    it.addMonster(action.monsterIds)
                        .copy(showMonsterDialog = false)
                }

                is ScenarioActions.RemoveMonster -> updateState { it.removeMonster(action.monsterId) }
                is ScenarioActions.AddUnits -> updateState {
                    it.addUnits(
                        numbers = action.numbers,
                        monsterId = action.monsterId,
                        isSpecial = action.isElite
                    )
                }

                is ScenarioActions.RemoveUnit -> updateState {
                    it.removeUnit(
                        number = action.number,
                        monsterId = action.monsterId
                    )
                }

                is ScenarioActions.UpdateUnitLife -> updateState {
                    it.updateUnitLife(
                        number = action.unitNumber,
                        monsterId = action.monsterId,
                        newValue = action.newValue
                    )
                }

                is ScenarioActions.NextRound -> updateState { it.nextRound() }
                is ScenarioActions.SwitchUnitEffect -> updateState {
                    it.addEffect(
                        number = action.unitNumber,
                        monsterId = action.monsterId,
                        effect = action.effect
                    )
                }

                is ScenarioActions.CompleteScenario -> {
                    if (scenarioNumber != null) {
                        viewModelScope.launch {
                            completeScenarioUseCase(scenarioNumber)
                            _navigationEvents.emit(GlHelperEvent.Back)
                        }
                    }
                }

                ScenarioActions.CloseMonstersDialog -> updateState {
                    it.copy(
                        showMonsterDialog = false
                    )
                }

                ScenarioActions.OpenMonstersDialog -> updateState {
                    it.copy(
                        showMonsterDialog = true
                    )
                }

                is ScenarioActions.UpdateMagic -> updateState { it.updateMagic(action.magic) }
                ScenarioActions.CloseUnitLevelDialog -> updateState {
                    it.copy(
                        showUnitLevelDialog = false
                    )
                }

                ScenarioActions.ShowUnitLevelDialog -> updateState {
                    it.copy(
                        showUnitLevelDialog = true
                    )
                }

                is ScenarioActions.UpdateUnitLevel -> updateState {
                    val newStats = getMonsterStatsForLevelUseCase(
                        action.monsterId,
                        action.level,
                        action.isElite
                    )
                    it.updateUnitStats(
                        monsterId = action.monsterId,
                        number = action.unitNumber,
                        stats = newStats
                    )
                }
            }
        }
    }

    private suspend fun updateState(
        update: suspend (ScenarioLogicState) -> ScenarioLogicState
    ) {
        val state = logicState.value ?: return
        withContext(Dispatchers.Default) {
            val newState = update(state)
            logicState.update { newState }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            scenarioNumber: Int?,
            restore: Boolean
        ): ScenarioViewModel
    }
}

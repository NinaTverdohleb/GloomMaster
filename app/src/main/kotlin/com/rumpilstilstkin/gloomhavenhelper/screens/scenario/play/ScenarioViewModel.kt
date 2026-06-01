package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.ClearCurrentActiveScenarioUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.CompleteScenarioUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.GetMonsterStatsForLevelUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play.GetScenarioInfoUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play.SaveScenarioStateUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreens
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.ScenarioActions
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.ScenarioLogicState
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.ScenarioStateMapper
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.ScenarioStateUi
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
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
@HiltViewModel
class ScenarioViewModel @Inject constructor(
    private val getScenarioInfoUseCase: GetScenarioInfoUseCase,
    private val completeScenarioUseCase: CompleteScenarioUseCase,
    private val getMonsterStatsForLevelUseCase: GetMonsterStatsForLevelUseCase,
    private val saveScenarioStateUseCase: SaveScenarioStateUseCase,
    private val clearCurrentActiveScenarioUseCase: ClearCurrentActiveScenarioUseCase,
) : ViewModel(), DefaultLifecycleObserver {
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

    override fun onResume(owner: LifecycleOwner) {
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
            getScenarioInfoUseCase().onSuccess { battleInfo ->
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
                    it.addMonster(action.monsterSlugs)
                }

                is ScenarioActions.RemoveMonster -> updateState { it.removeMonster(action.monsterSlug) }
                is ScenarioActions.AddUnits -> updateState {
                    it.addUnits(
                        numbers = action.numbers,
                        monsterSlug = action.monsterSlug,
                        isSpecial = action.isElite
                    )
                }

                is ScenarioActions.RemoveUnit -> updateState {
                    it.removeUnit(
                        number = action.number,
                        monsterSlug = action.monsterSlug
                    )
                }

                is ScenarioActions.UpdateUnitLife -> updateState {
                    it.updateUnitLife(
                        number = action.unitNumber,
                        monsterSlug = action.monsterSlug,
                        newValue = action.newValue
                    )
                }

                is ScenarioActions.NextRound -> updateState { it.nextRound() }
                is ScenarioActions.SwitchUnitEffect -> updateState {
                    it.addEffect(
                        number = action.unitNumber,
                        monsterSlug = action.monsterSlug,
                        effect = action.effect
                    )
                }

                is ScenarioActions.CompleteScenario -> {
                    viewModelScope.async {
                        val number = logicState.value?.scenarioInfo?.scenarioNumber
                        if (number != null) {
                            completeScenarioUseCase(number)
                        } else {
                            clearCurrentActiveScenarioUseCase()
                        }
                    }.await()
                    _navigationEvents.emit(GlHelperEvent.Back)
                }

                is ScenarioActions.UpdateMagic -> updateState { it.updateMagic(action.magic) }

                is ScenarioActions.UpdateUnitLevel -> updateState {
                    val newStats = getMonsterStatsForLevelUseCase(
                        action.monsterSlug,
                        action.level,
                        action.isElite
                    )
                    it.updateUnitStats(
                        monsterSlug = action.monsterSlug,
                        number = action.unitNumber,
                        stats = newStats
                    )
                }

                ScenarioActions.AddNewMonsters -> {
                    _navigationEvents.emit(
                        GlHelperEvent.Screen(GlHelperScreens.ScenarioConstructor)
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
}

package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.ClearCurrentActiveScenarioUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.CompleteScenarioUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play.AddMonsterToBattleUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play.AddMonsterUnitsUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play.GetScenarioInfoUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play.NextRoundUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play.RemoveMonsterUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play.RemoveUnitUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play.SaveScenarioStateUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play.ToggleMagicChargeUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play.ToggleUnitEffectUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play.UpdateUnitLevelUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.play.UpdateUnitLifeUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreens
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.ScenarioActions
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.ScenarioStateMapper
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.ScenarioStateUi
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
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
    private val saveScenarioStateUseCase: SaveScenarioStateUseCase,
    private val clearCurrentActiveScenarioUseCase: ClearCurrentActiveScenarioUseCase,
    private val addMonsterToBattleUseCase: AddMonsterToBattleUseCase,
    private val addMonsterUnitsUseCase: AddMonsterUnitsUseCase,
    private val nextRoundUseCase: NextRoundUseCase,
    private val removeMonsterUseCase: RemoveMonsterUseCase,
    private val removeUnitUseCase: RemoveUnitUseCase,
    private val toggleMagicChargeUseCase: ToggleMagicChargeUseCase,
    private val toggleUnitEffectUseCase: ToggleUnitEffectUseCase,
    private val updateUnitLevelUseCase: UpdateUnitLevelUseCase,
    private val updateUnitLifeUseCase: UpdateUnitLifeUseCase,
) : ViewModel() {
    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    private val logicState = MutableStateFlow<ScenarioBattleState?>(null)
    val uiState: StateFlow<ScenarioStateUi> =
        logicState
            .filterNotNull()
            .debounce(200)
            .map {
                ScenarioStateMapper.toUiState(it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(500),
                initialValue = ScenarioStateUi(),
            )

    init {
        loadScenario()
        logicState
            .filterNotNull()
            .map { logicState ->
                ScenarioStateMapper.stateForSave(logicState)
            }.distinctUntilChanged()
            .onEach { newState ->
                saveScenarioStateUseCase(newState)
            }.launchIn(viewModelScope)
    }

    private fun loadScenario() {
        viewModelScope.launch {
            delay(200)
            getScenarioInfoUseCase().onSuccess { battleInfo ->
                logicState.update { battleInfo }
            }
        }
    }

    fun onAction(action: ScenarioActions) {
        viewModelScope.launch {
            when (action) {
                is ScenarioActions.AddMonster -> {
                    updateState {
                        addMonsterToBattleUseCase(it, action.monsterSlugs)
                    }
                }

                is ScenarioActions.RemoveMonster -> {
                    updateState {
                        removeMonsterUseCase(
                            it,
                            action.monsterSlug,
                        )
                    }
                }

                is ScenarioActions.AddUnits -> {
                    updateState {
                        addMonsterUnitsUseCase(
                            state = it,
                            numbers = action.numbers,
                            slug = action.monsterSlug,
                            isElite = action.isElite,
                        )
                    }
                }

                is ScenarioActions.RemoveUnit -> {
                    updateState {
                        removeUnitUseCase(
                            state = it,
                            number = action.number,
                            slug = action.monsterSlug,
                        )
                    }
                }

                is ScenarioActions.UpdateUnitLife -> {
                    updateState {
                        updateUnitLifeUseCase(
                            state = it,
                            number = action.unitNumber,
                            slug = action.monsterSlug,
                            newLife = action.newValue,
                        )
                    }
                }

                is ScenarioActions.NextRound -> {
                    updateState { nextRoundUseCase(it) }
                }

                is ScenarioActions.SwitchUnitEffect -> {
                    updateState {
                        toggleUnitEffectUseCase(
                            state = it,
                            number = action.unitNumber,
                            slug = action.monsterSlug,
                            effect = action.effect,
                        )
                    }
                }

                is ScenarioActions.CompleteScenario -> {
                    viewModelScope
                        .async {
                            val number = logicState.value?.scenarioNumber
                            if (number != null) {
                                completeScenarioUseCase(number)
                            } else {
                                clearCurrentActiveScenarioUseCase()
                            }
                        }.await()
                    _navigationEvents.emit(GlHelperEvent.Back)
                }

                is ScenarioActions.UpdateMagic -> {
                    updateState {
                        toggleMagicChargeUseCase(
                            state = it,
                            magic = action.magic,
                        )
                    }
                }

                is ScenarioActions.UpdateUnitLevel -> {
                    updateState {
                        updateUnitLevelUseCase(
                            state = it,
                            slug = action.monsterSlug,
                            level = action.level,
                            isElite = action.isElite,
                            number = action.unitNumber,
                        )
                    }
                }

                ScenarioActions.AddNewMonsters -> {
                    _navigationEvents.emit(
                        GlHelperEvent.Screen(GlHelperScreens.ScenarioConstructor),
                    )
                }
            }
        }
    }

    private suspend fun updateState(update: suspend (ScenarioBattleState) -> ScenarioBattleState) {
        val state = logicState.value ?: return
        withContext(Dispatchers.Default) {
            val newState = update(state)
            logicState.update { newState }
        }
    }
}

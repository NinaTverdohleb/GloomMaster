package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ScenarioBattleState
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
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreen
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.screens.core.ScreenEffect
import com.rumpilstilstkin.gloomhavenhelper.screens.core.createOverlaySession
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.complete.CompleteScenarioDialogContract
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.complete.CompleteScenarioDialogInput
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.monsters.delete.DeleteMonsterDialogContract
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.monsters.delete.DeleteMonsterDialogInput
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.monsters.MonsterListDialogContract
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.monsters.MonsterListDialogInput
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.monsters.MonsterListDialogResult
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.ScenarioActions
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.ScenarioStateMapper
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.ScenarioStateUi
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.stats.ScenarioStatsDialogContract
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.stats.ScenarioStatsDialogInput
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.monsters.unit.AddMonsterUnitDialogContract
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.monsters.unit.AddMonsterUnitDialogInput
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
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
    private val saveScenarioStateUseCase: SaveScenarioStateUseCase,
    private val addMonsterToBattleUseCase: AddMonsterToBattleUseCase,
    private val addMonsterUnitsUseCase: AddMonsterUnitsUseCase,
    private val nextRoundUseCase: NextRoundUseCase,
    private val removeMonsterUseCase: RemoveMonsterUseCase,
    private val removeUnitUseCase: RemoveUnitUseCase,
    private val toggleMagicChargeUseCase: ToggleMagicChargeUseCase,
    private val toggleUnitEffectUseCase: ToggleUnitEffectUseCase,
    private val updateUnitLevelUseCase: UpdateUnitLevelUseCase,
    private val updateUnitLifeUseCase: UpdateUnitLifeUseCase,
) : ViewModel(),
    DefaultLifecycleObserver {
    private val _screenEvents = MutableSharedFlow<ScreenEffect>()
    val screenEvents = _screenEvents.asSharedFlow()

    private val logicState = MutableStateFlow<ScenarioBattleState?>(null)
    val uiState: StateFlow<ScenarioStateUi> =
        logicState
            .filterNotNull()
            .debounce(200)
            .map {
                ScenarioStateMapper.toUiState(it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = ScenarioStateUi(),
            )

    init {
        viewModelScope.launch {
            delay(100)
            getScenarioInfoUseCase().onSuccess { battleInfo ->
                logicState.update { battleInfo }
            }
        }
        logicState
            .filterNotNull()
            .map { logicState ->
                ScenarioStateMapper.stateForSave(logicState)
            }.distinctUntilChanged()
            .onEach { newState ->
                saveScenarioStateUseCase(newState)
            }.launchIn(viewModelScope)
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModelScope.launch {
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
                    openDeleteMonsterDialog(action.monsterSlug)
                }

                is ScenarioActions.AddUnits -> {
                    openAddUnitsDialog(
                        monsterSlug = action.monsterSlug,
                        monsterName = action.monsterName,
                        unitNumbers = action.unitNumbers,
                    )
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
                    _screenEvents.emit(
                        ScreenEffect.Navigation(
                            GlHelperEvent.Screen(GlHelperScreen.ScenarioConstructor),
                        )
                    )
                }

                ScenarioActions.OpenAddMonster -> {
                    openAddMonsterDialog(
                        monsters = uiState.value.monstersForAdd
                    )
                }

                ScenarioActions.OpenComplete -> {
                    openCompleteDialog()
                }

                ScenarioActions.OpenStats -> {
                    openStatsDialog()
                }
            }
        }
    }

    private suspend fun openCompleteDialog() {
        val state = uiState.value
        val session =
            createOverlaySession(
                contract = CompleteScenarioDialogContract,
                input = CompleteScenarioDialogInput(
                    exp = state.exp,
                    gold = state.gold,
                ),
                onResult = { result ->
                    result?.let {
                        viewModelScope.launch {
                            _screenEvents.emit(ScreenEffect.Navigation(GlHelperEvent.Back))
                        }
                    }
                },
            )
        _screenEvents.emit(ScreenEffect.OpenDialog(session))
    }

    private suspend fun openStatsDialog() {
        val state = uiState.value
        val session =
            createOverlaySession(
                contract = ScenarioStatsDialogContract,
                input = ScenarioStatsDialogInput(
                    level = state.level,
                    exp = state.exp,
                    gold = state.gold,
                    trapDamage = state.trapDamage,
                ),
                onResult = { },
            )
        _screenEvents.emit(ScreenEffect.OpenDialog(session))
    }

    private suspend fun openAddMonsterDialog(
        monsters: List<MonsterItem>
    ) {
        val session =
            createOverlaySession(
                contract = MonsterListDialogContract,
                input = MonsterListDialogInput(monsters = monsters),
                onResult = { result ->
                    when (result) {
                        is MonsterListDialogResult.Selected ->
                            onAction(ScenarioActions.AddMonster(result.slugs))

                        MonsterListDialogResult.AddNewMonsters ->
                            onAction(ScenarioActions.AddNewMonsters)

                        null -> Unit
                    }
                },
            )
        _screenEvents.emit(ScreenEffect.OpenBottomSheet(session))
    }

    private suspend fun openDeleteMonsterDialog(monsterSlug: String) {
        val monsterName =
            uiState.value.monsters.firstOrNull { it.slug == monsterSlug }?.name.orEmpty()
        val session =
            createOverlaySession(
                contract = DeleteMonsterDialogContract,
                input = DeleteMonsterDialogInput(
                    monsterSlug = monsterSlug,
                    monsterName = monsterName,
                ),
                onResult = { result ->
                    result?.let {
                        viewModelScope.launch {
                            updateState {
                                removeMonsterUseCase(it, monsterSlug)
                            }
                        }
                    }
                },
            )
        _screenEvents.emit(ScreenEffect.OpenDialog(session))
    }

    private suspend fun updateState(update: suspend (ScenarioBattleState) -> ScenarioBattleState) {
        val state = logicState.value ?: return
        withContext(Dispatchers.Default) {
            val newState = update(state)
            logicState.update { newState }
        }
    }

    private suspend fun openAddUnitsDialog(
        monsterSlug: String,
        monsterName: String,
        unitNumbers: List<Int>
    ) {
        val session =
            createOverlaySession(
                contract = AddMonsterUnitDialogContract,
                input = AddMonsterUnitDialogInput(
                    monsterSlug = monsterSlug,
                    monsterName = monsterName,
                    availableIds = unitNumbers.toImmutableList(),
                ),
                onResult = { output ->
                    output?.let { result ->
                        viewModelScope.launch {
                            updateState {
                                addMonsterUnitsUseCase(
                                    state = it,
                                    numbers = result.numbers,
                                    slug = result.monsterSlug,
                                    isElite = result.isElite,
                                )
                            }
                        }
                    }
                },
            )
        _screenEvents.emit(ScreenEffect.OpenDialog(session))
    }
}

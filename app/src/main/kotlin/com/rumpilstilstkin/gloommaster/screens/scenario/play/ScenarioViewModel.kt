package com.rumpilstilstkin.gloommaster.screens.scenario.play

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloommaster.domain.entity.monster.MonsterName
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.MonsterUnit
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.ScenarioBattleState
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play.AddMonsterToBattleUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play.AddMonsterUnitsUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play.GetScenarioInfoUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play.NextRoundUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play.RemoveMonsterUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play.RemoveUnitUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play.SaveScenarioStateUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play.ToggleMagicChargeUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play.ToggleUnitEffectUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play.UpdateUnitLevelUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.play.UpdateUnitLifeUseCase
import com.rumpilstilstkin.gloommaster.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloommaster.screens.core.ScreenEffect
import com.rumpilstilstkin.gloommaster.screens.core.createOverlaySession
import com.rumpilstilstkin.gloommaster.screens.scenario.play.complete.CompleteScenarioDialogContract
import com.rumpilstilstkin.gloommaster.screens.scenario.play.complete.CompleteScenarioDialogInput
import com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters.MonsterListDialogContract
import com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters.MonsterListDialogInput
import com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters.MonsterListDialogResult
import com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters.add.AddScenarioMonstersDialogContract
import com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters.delete.DeleteMonsterDialogContract
import com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters.delete.DeleteMonsterDialogInput
import com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters.level.MonsterLevelDialogContract
import com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters.level.MonsterLevelDialogInput
import com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters.unit.add.AddMonsterUnitDialogContract
import com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters.unit.add.AddMonsterUnitDialogInput
import com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters.unit.delete.DeleteUnitDialogContract
import com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters.unit.delete.DeleteUnitDialogInput
import com.rumpilstilstkin.gloommaster.screens.scenario.play.state.ScenarioActions
import com.rumpilstilstkin.gloommaster.screens.scenario.play.state.ScenarioStateMapper
import com.rumpilstilstkin.gloommaster.screens.scenario.play.state.ScenarioStateUi
import com.rumpilstilstkin.gloommaster.screens.scenario.play.stats.ScenarioStatsDialogContract
import com.rumpilstilstkin.gloommaster.screens.scenario.play.stats.ScenarioStatsDialogInput
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
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
    private val _screenEvents = Channel<ScreenEffect>(Channel.BUFFERED)
    val screenEvents = _screenEvents.receiveAsFlow()

    private val logicState = MutableStateFlow<ScenarioBattleState?>(null)
    val uiState: StateFlow<ScenarioStateUi> =
        logicState
            .filterNotNull()
            .distinctUntilChanged()
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
                    val monster = logicState.value?.activeMonsters[action.monsterSlug] ?: return@launch
                    val existingNumbers = monster.units.keys
                    val avaliableNumber = (1..15).toList().filter { it !in existingNumbers }
                    openAddUnitsDialog(
                        monsterSlug = monster.slug,
                        monsterName = monster.name,
                        unitNumbers = avaliableNumber,
                    )
                }

                is ScenarioActions.RemoveUnit -> {
                    openDeleteUnitDialog(
                        monsterSlug = action.monsterSlug,
                        number = action.number,
                    )
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
                    val monsterUnit = logicState.value?.getUnit(action.monsterSlug, action.unitNumber) ?: return@launch
                    openUnitLevelDialog(
                        unit = monsterUnit,
                        monsterSlug = action.monsterSlug,
                        monsterName = logicState.value?.monsters[action.monsterSlug]?.name ?: "",
                    )
                }

                ScenarioActions.AddNewMonsters -> {
                    openAddMonsterForScenarioDialog()
                }

                ScenarioActions.OpenAddMonster -> {
                    openAddMonsterDialog(
                        monsters = uiState.value.monstersForAdd,
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

    private suspend fun openUnitLevelDialog(
        monsterName: String,
        monsterSlug: String,
        unit: MonsterUnit,
    ) {
        val session =
            createOverlaySession(
                contract = MonsterLevelDialogContract,
                input =
                    MonsterLevelDialogInput(
                        unitLevel = unit.level,
                        unitNumber = unit.number,
                        monsterName = monsterName,
                    ),
                onResult = { result ->
                    result?.let { level ->
                        viewModelScope.launch {
                            updateState {
                                updateUnitLevelUseCase(
                                    state = it,
                                    slug = monsterSlug,
                                    level = level,
                                    isElite = unit.isSpecial,
                                    number = unit.number,
                                )
                            }
                        }
                    }
                },
            )
        _screenEvents.send(ScreenEffect.OpenDialog(session))
    }

    private suspend fun openAddMonsterForScenarioDialog() {
        val session =
            createOverlaySession(
                contract = AddScenarioMonstersDialogContract,
                input = Unit,
                onResult = { result ->
                    result?.let { monsters ->
                        viewModelScope.launch {
                            getScenarioInfoUseCase().onSuccess { battleInfo ->
                                logicState.update { battleInfo }
                                onAction(ScenarioActions.AddMonster(monsters))
                            }
                        }
                    }
                },
            )
        _screenEvents.send(ScreenEffect.OpenDialog(session))
    }

    private suspend fun openCompleteDialog() {
        val state = uiState.value
        val session =
            createOverlaySession(
                contract = CompleteScenarioDialogContract,
                input =
                    CompleteScenarioDialogInput(
                        exp = state.exp,
                        gold = state.gold,
                    ),
                onResult = { result ->
                    result?.let {
                        viewModelScope.launch {
                            _screenEvents.send(ScreenEffect.Navigation(GlHelperEvent.Back))
                        }
                    }
                },
            )
        _screenEvents.send(ScreenEffect.OpenDialog(session))
    }

    private suspend fun openStatsDialog() {
        val state = uiState.value
        val session =
            createOverlaySession(
                contract = ScenarioStatsDialogContract,
                input =
                    ScenarioStatsDialogInput(
                        level = state.level,
                        exp = state.exp,
                        gold = state.gold,
                        trapDamage = state.trapDamage,
                        scenarioNumber = state.scenarioNumber,
                        location = state.scenarioLocation,
                        scenarioName = state.scenarioName,
                    ),
                onResult = { },
            )
        _screenEvents.send(ScreenEffect.OpenDialog(session))
    }

    private suspend fun openAddMonsterDialog(monsters: List<MonsterName>) {
        val session =
            createOverlaySession(
                contract = MonsterListDialogContract,
                input = MonsterListDialogInput(monsters = monsters),
                onResult = { result ->
                    when (result) {
                        is MonsterListDialogResult.Selected -> {
                            onAction(ScenarioActions.AddMonster(result.slugs))
                        }

                        null -> { }
                    }
                },
            )
        _screenEvents.send(ScreenEffect.OpenBottomSheet(session))
    }

    private fun ScenarioBattleState.getUnit(
        slug: String,
        number: Int,
    ): MonsterUnit? = activeMonsters[slug]?.units[number]

    private suspend fun openDeleteMonsterDialog(monsterSlug: String) {
        val monsterName =
            uiState.value.monsters
                .firstOrNull { it.slug == monsterSlug }
                ?.name
                .orEmpty()
        val session =
            createOverlaySession(
                contract = DeleteMonsterDialogContract,
                input =
                    DeleteMonsterDialogInput(
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
        _screenEvents.send(ScreenEffect.OpenDialog(session))
    }

    private suspend fun openDeleteUnitDialog(
        monsterSlug: String,
        number: Int,
    ) {
        val monsterName =
            logicState.value
                ?.monsters[monsterSlug]
                ?.name
                .orEmpty()
        val session =
            createOverlaySession(
                contract = DeleteUnitDialogContract,
                input =
                    DeleteUnitDialogInput(
                        monsterSlug = monsterSlug,
                        monsterName = monsterName,
                        unitNumber = number,
                    ),
                onResult = { result ->
                    result?.let {
                        viewModelScope.launch {
                            updateState {
                                removeUnitUseCase(
                                    state = it,
                                    number = number,
                                    slug = monsterSlug,
                                )
                            }
                        }
                    }
                },
            )
        _screenEvents.send(ScreenEffect.OpenDialog(session))
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
        unitNumbers: List<Int>,
    ) {
        val session =
            createOverlaySession(
                contract = AddMonsterUnitDialogContract,
                input =
                    AddMonsterUnitDialogInput(
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
        _screenEvents.send(ScreenEffect.OpenDialog(session))
    }
}

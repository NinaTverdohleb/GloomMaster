package com.rumpilstilstkin.gloomhavenhelper.screens.scenario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.CompleteScenarioUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.GetScenarioInfoUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
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
import kotlinx.coroutines.withContext

@HiltViewModel(assistedFactory = ScenarioViewModel.Factory::class)
class ScenarioViewModel @AssistedInject constructor(
    private val getScenarioInfoUseCase: GetScenarioInfoUseCase,
    private val completeScenarioUseCase: CompleteScenarioUseCase,
    @Assisted private val scenarioNumber: Int,
) : ViewModel() {
    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    private val _logicState = MutableStateFlow<ScenarioLogicState?>(null)
    val uiState: StateFlow<ScenarioUIState> = _logicState
        .filterNotNull()
        .map {
            withContext(Dispatchers.Default) {
                it.toUIState()
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ScenarioUIState()
        )

    init {
        loadScenario()
    }

    private fun loadScenario() {
        viewModelScope.launch {
            val battleInfo = getScenarioInfoUseCase(scenarioNumber)
            _logicState.value = ScenarioLogicState(
                scenarioInfo = battleInfo,
                availableCards = battleInfo.monsters.flatMap { it.cards }.distinct().toImmutableList(),
                activeMonsters = persistentListOf(),
                round = 0
            )
        }
    }

    fun onAction(action: ScenarioActions) {
        val state = _logicState.value ?: return
        viewModelScope.launch(Dispatchers.Default) {
            val newState = state.let {
                when (action) {
                    is ScenarioActions.AddMonster -> it
                        .addMonster(action.monsterIds)
                        .copy(showMonsterDialog = false)

                    is ScenarioActions.RemoveMonster -> it.removeMonster(action.monsterId)
                    is ScenarioActions.AddUnits -> it.addUnits(
                        numbers = action.numbers,
                        monsterId = action.monsterId,
                        isSpecial = action.isElite
                    )

                    is ScenarioActions.RemoveUnit -> it.removeUnit(
                        number = action.number,
                        monsterId = action.monsterId
                    )

                    is ScenarioActions.UpdateUnitLife -> it.updateUnitLife(
                        number = action.unitNumber,
                        monsterId = action.monsterId,
                        newValue = action.newValue
                    )

                    is ScenarioActions.NextRound -> it.nextRound()
                    is ScenarioActions.SwitchUnitEffect -> it.addEffect(
                        number = action.unitNumber,
                        monsterId = action.monsterId,
                        effect = action.effect
                    )

                    is ScenarioActions.CompleteScenario -> {
                        viewModelScope.launch {
                            completeScenarioUseCase(scenarioNumber)
                            _navigationEvents.emit(GlHelperEvent.Back)
                        }
                        it
                    }

                    ScenarioActions.CloseMonstersDialog -> it.copy(
                        showMonsterDialog = false
                    )

                    ScenarioActions.OpenMonstersDialog -> it.copy(
                        showMonsterDialog = true
                    )

                    is ScenarioActions.UpdateMagic -> it.updateMagic(action.magic)
                }
            }
            _logicState.update { newState }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(scenarioNumber: Int): ScenarioViewModel
    }
}

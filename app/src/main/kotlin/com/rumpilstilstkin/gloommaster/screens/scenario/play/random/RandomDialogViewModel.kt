package com.rumpilstilstkin.gloommaster.screens.scenario.play.random

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloommaster.domain.entity.monster.MonsterName
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.AddMonstersForCurrentScenarioUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.GetAvailableMonstersForTeamUseCase
import com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters.add.AddScenarioMonstersDialogAction
import com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters.add.AddScenarioMonstersDialogResult
import com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters.add.AddScenarioMonstersDialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class RandomDialogViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow<RandomDialogState>(RandomDialogState.Empty)
    val state: StateFlow<RandomDialogState> = _state.asStateFlow()

    fun onAction(action: RandomDialogAction) {
        val currentState = _state.value
        viewModelScope.launch {
            when (action) {
                is RandomDialogAction.Coin -> {
                    val lastResult = (currentState as? RandomDialogState.Coin)?.result
                    val result = getNewResult(lastResult)
                    _state.update {
                        RandomDialogState.Coin(result)
                    }
                }

                RandomDialogAction.Weird -> {
                    val lastResult = (currentState as? RandomDialogState.Weird)?.result
                    val result = getNewResult(lastResult)
                    _state.update {
                        RandomDialogState.Weird(result)
                    }
                }
            }
        }
    }

    private fun getNewResult(lastResult: Int?): Int {
        var result: Int
        do {
            result = Random.nextInt(1, 16)
        } while (result == lastResult)
        return result
    }
}

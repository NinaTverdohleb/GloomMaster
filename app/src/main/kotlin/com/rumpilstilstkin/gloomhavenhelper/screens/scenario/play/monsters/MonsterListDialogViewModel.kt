package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.monsters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = MonsterListDialogViewModel.Factory::class)
class MonsterListDialogViewModel @AssistedInject constructor(
    @Assisted private val input: MonsterListDialogInput,
) : ViewModel() {
    private val _state =
        MutableStateFlow(MonsterListDialogState(monsters = input.monsters.toImmutableList()))
    val state: StateFlow<MonsterListDialogState> = _state.asStateFlow()

    private val _complete = Channel<MonsterListDialogResult>(Channel.BUFFERED)
    val complete = _complete.receiveAsFlow()

    fun onAction(action: MonsterListDialogAction) {
        when (action) {
            is MonsterListDialogAction.ToggleMonster -> {
                _state.update { state ->
                    val selected =
                        if (state.selectedIds.contains(action.slug)) {
                            state.selectedIds - action.slug
                        } else {
                            state.selectedIds + action.slug
                        }
                    state.copy(selectedIds = selected.toImmutableList())
                }
            }

            MonsterListDialogAction.SelectMonsters -> {
                viewModelScope.launch {
                    _complete.send(MonsterListDialogResult.Selected(_state.value.selectedIds))
                }
            }

            MonsterListDialogAction.AddNewMonsters -> {
                viewModelScope.launch {
                    _complete.send(MonsterListDialogResult.AddNewMonsters)
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(input: MonsterListDialogInput): MonsterListDialogViewModel
    }
}

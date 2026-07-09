package com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters.unit.add

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

@HiltViewModel(assistedFactory = AddMonsterUnitDialogViewModel.Factory::class)
class AddMonsterUnitDialogViewModel @AssistedInject constructor(
    @Assisted private val input: AddMonsterUnitDialogInput,
) : ViewModel() {
    private val _state =
        MutableStateFlow(
            AddMonsterUnitDialogState(
                monsterName = input.monsterName,
                availableIds = input.availableIds,
            ),
        )
    val state: StateFlow<AddMonsterUnitDialogState> = _state.asStateFlow()

    private val _complete = Channel<AddMonsterUnitDialogResult>(Channel.BUFFERED)
    val complete = _complete.receiveAsFlow()

    fun onAction(action: AddMonsterUnitDialogAction) {
        when (action) {
            is AddMonsterUnitDialogAction.SelectTier -> {
                _state.update { it.copy(selectedTier = action.tier) }
            }

            is AddMonsterUnitDialogAction.ToggleId -> {
                _state.update { state ->
                    val selected =
                        if (state.selectedIds.contains(action.number)) {
                            state.selectedIds - action.number
                        } else {
                            state.selectedIds + action.number
                        }
                    state.copy(selectedIds = selected.toImmutableList())
                }
            }

            AddMonsterUnitDialogAction.Spawn -> {
                val state = _state.value
                viewModelScope.launch {
                    _complete.send(
                        AddMonsterUnitDialogResult(
                            monsterSlug = input.monsterSlug,
                            numbers = state.selectedIds,
                            isElite = state.selectedTier == UnitTier.Elite,
                        ),
                    )
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(input: AddMonsterUnitDialogInput): AddMonsterUnitDialogViewModel
    }
}

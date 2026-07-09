package com.rumpilstilstkin.gloommaster.screens.characters.dialogs.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloommaster.domain.usecase.characters.CreateCharacterUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.classes.GetAvaliableClassesForCurrentTeamUseCase
import com.rumpilstilstkin.gloommaster.screens.models.CharacterClassTypeUI.Companion.toCharacterClassTypeUI
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AddCharacterDialogViewModel @Inject constructor(
    getAvaliableClassesForCurrentTeamUseCase: GetAvaliableClassesForCurrentTeamUseCase,
    private val createCharacterUseCase: CreateCharacterUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(AddCharacterDialogState())
    val state: StateFlow<AddCharacterDialogState> = _state.asStateFlow()

    private val _complete = Channel<AddCharacterDialogComplete>(Channel.BUFFERED)
    val complete = _complete.receiveAsFlow()

    init {
        getAvaliableClassesForCurrentTeamUseCase()
            .map { classes -> classes.map { it.toCharacterClassTypeUI() }.toImmutableList() }
            .onEach { available ->
                _state.update { state ->
                    state.copy(
                        avaliableClasses = available,
                        selectedClass = available.firstOrNull() ?: state.selectedClass,
                    )
                }
            }.launchIn(viewModelScope)
    }

    fun onAction(action: AddCharacterDialogAction) {
        when (action) {
            is AddCharacterDialogAction.AddCharacter -> addCharacter()
            is AddCharacterDialogAction.SelectType -> _state.update { it.copy(selectedClass = action.type) }
            is AddCharacterDialogAction.UpdateLevel -> _state.update { it.copy(level = action.level) }
            is AddCharacterDialogAction.UpdateName -> _state.update { it.copy(name = action.name) }
        }
    }

    private fun addCharacter() {
        viewModelScope.launch {
            val current = _state.value
            createCharacterUseCase(
                name = current.name,
                level = current.level,
                characterType = current.selectedClass.type,
            )
            _complete.send(AddCharacterDialogComplete)
        }
    }
}

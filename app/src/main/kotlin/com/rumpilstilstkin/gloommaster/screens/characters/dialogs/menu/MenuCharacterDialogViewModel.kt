package com.rumpilstilstkin.gloommaster.screens.characters.dialogs.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloommaster.domain.usecase.characters.MakeCharacterAliveUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.characters.RetireCharacterUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.characters.UpdateCharacterLevelUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = MenuCharacterDialogViewModel.Factory::class)
class MenuCharacterDialogViewModel @AssistedInject constructor(
    @Assisted val level: Int,
    private val updateCharacterLevelUseCase: UpdateCharacterLevelUseCase,
    private val retireCharacterUseCase: RetireCharacterUseCase,
    private val makeCharacterAliveUseCase: MakeCharacterAliveUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(level)
    val state: StateFlow<Int> = _state.asStateFlow()

    @AssistedFactory
    interface Factory {
        fun create(level: Int): MenuCharacterDialogViewModel
    }

    private val _complete = Channel<MenuCharacterDialogComplete>(Channel.BUFFERED)
    val complete = _complete.receiveAsFlow()

    fun onAction(action: MenuCharacterDialogAction) {
        viewModelScope.launch {
            when (action) {
                is MenuCharacterDialogAction.UpdateLevel -> {
                    updateCharacterLevelUseCase(action.character.id, action.level)
                    _state.emit(action.level)
                }

                is MenuCharacterDialogAction.LeaveCharacter -> {
                    retireCharacterUseCase(action.character.id)
                    _complete.send(
                        MenuCharacterDialogComplete(
                            MenuCharacterResult.CharacterLeft(action.character),
                        ),
                    )
                }

                is MenuCharacterDialogAction.MakeCharacterAlive -> {
                    makeCharacterAliveUseCase(action.character.id)
                    _complete.send(
                        MenuCharacterDialogComplete(
                            MenuCharacterResult.CharacterMadeAlive(action.character),
                        ),
                    )
                }

                is MenuCharacterDialogAction.OpenCharacterDetails -> {
                    _complete.send(
                        MenuCharacterDialogComplete(
                            MenuCharacterResult.OpenCharacterDetails(action.character),
                        ),
                    )
                }

                is MenuCharacterDialogAction.DeleteCharacter -> {
                    _complete.send(
                        MenuCharacterDialogComplete(
                            MenuCharacterResult.DeleteCharacterRequest(action.character),
                        ),
                    )
                }
            }
        }
    }
}

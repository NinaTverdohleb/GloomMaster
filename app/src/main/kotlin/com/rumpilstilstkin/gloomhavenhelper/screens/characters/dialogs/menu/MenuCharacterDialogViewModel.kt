package com.rumpilstilstkin.gloomhavenhelper.screens.characters.dialogs.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.MakeCharacterAliveUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.RetireCharacterUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.UpdateCharacterLevelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MenuCharacterDialogViewModel @Inject constructor(
    private val updateCharacterLevelUseCase: UpdateCharacterLevelUseCase,
    private val retireCharacterUseCase: RetireCharacterUseCase,
    private val makeCharacterAliveUseCase: MakeCharacterAliveUseCase,
) : ViewModel() {

    private val _complete = Channel<MenuCharacterDialogComplete>(Channel.BUFFERED)
    val complete = _complete.receiveAsFlow()

    fun onAction(action: MenuCharacterDialogAction) {
        viewModelScope.launch {
            when (action) {
                is MenuCharacterDialogAction.UpdateLevel -> {
                    updateCharacterLevelUseCase(action.character.id, action.level)
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

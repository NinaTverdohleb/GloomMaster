package com.rumpilstilstkin.gloommaster.screens.characters.dialogs.delete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloommaster.domain.usecase.characters.DeleteCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DeleteCharacterDialogViewModel @Inject constructor(
    private val deleteCharacterUseCase: DeleteCharacterUseCase,
) : ViewModel() {
    private val _complete = Channel<DeleteCharacterDialogComplete>(Channel.BUFFERED)
    val complete = _complete.receiveAsFlow()

    fun onAction(action: DeleteCharacterDialogAction) {
        viewModelScope.launch {
            when (action) {
                is DeleteCharacterDialogAction.DeleteCharacter -> {
                    deleteCharacterUseCase(action.characterId)
                    _complete.send(DeleteCharacterDialogComplete)
                }
            }
        }
    }
}

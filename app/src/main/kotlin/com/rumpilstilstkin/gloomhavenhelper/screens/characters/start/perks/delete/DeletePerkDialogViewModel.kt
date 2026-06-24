package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.perks.delete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.perks.DeleteCharacterPerkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DeletePerkDialogViewModel @Inject constructor(
    private val deleteCharacterPerkUseCase: DeleteCharacterPerkUseCase,
) : ViewModel() {
    private val _complete = Channel<DeletePerkDialogComplete>(Channel.BUFFERED)
    val complete = _complete.receiveAsFlow()

    fun onAction(action: DeletePerkDialogAction) {
        viewModelScope.launch {
            when (action) {
                is DeletePerkDialogAction.DeletePerk -> {
                    deleteCharacterPerkUseCase(
                        characterId = action.characterId,
                        perkId = action.perkId,
                    )
                    _complete.send(DeletePerkDialogComplete)
                }
            }
        }
    }
}

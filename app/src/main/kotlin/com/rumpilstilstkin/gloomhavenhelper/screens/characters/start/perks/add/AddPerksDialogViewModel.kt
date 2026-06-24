package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.perks.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.perks.AddPerksForCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AddPerksDialogViewModel @Inject constructor(
    private val addPerksForCharacterUseCase: AddPerksForCharacterUseCase,
) : ViewModel() {
    private val _complete = Channel<AddPerksDialogComplete>(Channel.BUFFERED)
    val complete = _complete.receiveAsFlow()

    fun onAction(action: AddPerksDialogAction) {
        viewModelScope.launch {
            when (action) {
                is AddPerksDialogAction.AddPerks -> {
                    addPerksForCharacterUseCase(
                        perksIds = action.perksIds,
                        characterId = action.characterId,
                    )
                    _complete.send(AddPerksDialogComplete)
                }
            }
        }
    }
}

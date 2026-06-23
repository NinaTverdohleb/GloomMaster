package com.rumpilstilstkin.gloomhavenhelper.screens.characters.dialogs.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.CreateCharacterUseCase
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.dialog.add.AddScenarioDialogComplete
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AddCharacterDialogViewModel @Inject constructor(
    private val createCharacterUseCase: CreateCharacterUseCase
) : ViewModel() {

    private val _complete = Channel<AddCharacterDialogComplete>(Channel.BUFFERED)
    val complete = _complete.receiveAsFlow()

    fun onAction(action: AddCharacterDialogAction) {
        viewModelScope.launch {
            when (action) {
                is AddCharacterDialogAction.AddCharacter -> {}

                is AddCharacterDialogAction.SelectType -> TODO()
                is AddCharacterDialogAction.UpdateLevel -> TODO()
                is AddCharacterDialogAction.UpdateName -> TODO()
            }
        }
    }
}

package com.rumpilstilstkin.gloomhavenhelper.screens.characters.dialogs.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.GetCharacterGeneralInfoUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.UpdateCharacterNameUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CharacterEditNameDialogViewModel.Factory::class)
class CharacterEditNameDialogViewModel @AssistedInject constructor(
    @Assisted val characterId: Int,
    private val getCharacterGeneralInfoUseCase: GetCharacterGeneralInfoUseCase,
    private val updateCharacterNameUseCase: UpdateCharacterNameUseCase,
) : ViewModel() {
    @AssistedFactory
    interface Factory {
        fun create(characterId: Int): CharacterEditNameDialogViewModel
    }

    private val _uiState = MutableStateFlow(CharacterEditNameDialogUiState())
    val uiState = _uiState.asStateFlow()

    private val _complete = Channel<CharacterEditNameDialogComplete>(Channel.BUFFERED)
    val complete = _complete.receiveAsFlow()

    init {
        viewModelScope.launch {
            val name = getCharacterGeneralInfoUseCase(characterId)?.name.orEmpty()
            _uiState.update { it.copy(name = name) }
        }
    }

    fun onAction(action: CharacterEditNameDialogAction) {
        viewModelScope.launch {
            when (action) {
                is CharacterEditNameDialogAction.Save -> {
                    updateCharacterNameUseCase(characterId, action.name)
                    _complete.send(CharacterEditNameDialogComplete)
                }
            }
        }
    }
}

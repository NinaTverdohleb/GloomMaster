package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.text

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.GetCharacterDetailsInfoUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.UpdateNotesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
@HiltViewModel(assistedFactory = EditTextDialogViewModel.Factory::class)
class EditTextDialogViewModel @AssistedInject constructor(
    @Assisted val characterId: Int,
    getCharacterUseCase: GetCharacterDetailsInfoUseCase,
    private val updateNotesUseCase: UpdateNotesUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow("")
    val uiState = _uiState.asStateFlow()

    @AssistedFactory
    interface Factory {
        fun create(characterId: Int): EditTextDialogViewModel
    }

    init {
        viewModelScope.launch {
            val character = getCharacterUseCase(characterId).first()
            _uiState.value = character?.generalInfo?.notes ?: ""
        }
        _uiState
            .filterNotNull()
            .debounce(200.milliseconds)
            .distinctUntilChanged()
            .onEach { newState ->
                updateNotesUseCase(
                    characterId = characterId,
                    newNotes = newState,
                )
            }.launchIn(viewModelScope)
    }

    fun onAction(action: EditTextDialogAction) {
        viewModelScope.launch {
            when (action) {
                is EditTextDialogAction.UpdateText -> {
                    _uiState.value = action.text
                }
            }
        }
    }
}

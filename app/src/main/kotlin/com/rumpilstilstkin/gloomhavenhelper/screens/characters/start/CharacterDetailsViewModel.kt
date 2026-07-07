package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.GetCharacterGeneralInfoFlowUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.dialogs.character.CharacterEditNameDialogContract
import com.rumpilstilstkin.gloomhavenhelper.screens.core.ScreenEffect
import com.rumpilstilstkin.gloomhavenhelper.screens.core.createOverlaySession
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toUi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CharacterDetailsViewModel.Factory::class)
class CharacterDetailsViewModel @AssistedInject constructor(
    @Assisted val id: Int,
    getCharacterUseCase: GetCharacterGeneralInfoFlowUseCase,
) : ViewModel() {
    private val _screenEvents = MutableSharedFlow<ScreenEffect>()
    val screenEvents = _screenEvents.asSharedFlow()

    val uiState: StateFlow<CharacterDetailsStateUi> =
        getCharacterUseCase(id)
            .filterNotNull()
            .map { character ->
                CharacterDetailsStateUi.Data(
                    character = character.toUi(),
                )
            }.stateIn(
                scope = viewModelScope,
                initialValue = CharacterDetailsStateUi.Loading,
                started = SharingStarted.WhileSubscribed(5000),
            )

    @AssistedFactory
    interface Factory {
        fun create(id: Int): CharacterDetailsViewModel
    }

    fun onAction(action: CharacterDetailsAction) {
        viewModelScope.launch {
            when (action) {
                is CharacterDetailsAction.Back -> {
                    _screenEvents.emit(ScreenEffect.Navigation((GlHelperEvent.Back)))
                }

                is CharacterDetailsAction.OpenNameDialog -> {
                    openCharacterNameDialog()
                }
            }
        }
    }

    private fun openCharacterNameDialog() {
        viewModelScope.launch {
            val session =
                createOverlaySession(
                    contract = CharacterEditNameDialogContract,
                    input = id,
                    onResult = {},
                )
            _screenEvents.emit(ScreenEffect.OpenDialog(session))
        }
    }
}

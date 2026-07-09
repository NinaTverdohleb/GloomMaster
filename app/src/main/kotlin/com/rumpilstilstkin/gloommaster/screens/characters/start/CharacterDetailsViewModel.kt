package com.rumpilstilstkin.gloommaster.screens.characters.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloommaster.domain.usecase.characters.GetCharacterGeneralInfoFlowUseCase
import com.rumpilstilstkin.gloommaster.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloommaster.screens.characters.dialogs.character.CharacterEditNameDialogContract
import com.rumpilstilstkin.gloommaster.screens.core.ScreenEffect
import com.rumpilstilstkin.gloommaster.screens.core.createOverlaySession
import com.rumpilstilstkin.gloommaster.screens.models.toUi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CharacterDetailsViewModel.Factory::class)
class CharacterDetailsViewModel @AssistedInject constructor(
    @Assisted val id: Int,
    getCharacterUseCase: GetCharacterGeneralInfoFlowUseCase,
) : ViewModel() {
    private val _screenEvents = Channel<ScreenEffect>(Channel.BUFFERED)
    val screenEvents = _screenEvents.receiveAsFlow()

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
                    _screenEvents.send(ScreenEffect.Navigation((GlHelperEvent.Back)))
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
            _screenEvents.send(ScreenEffect.OpenDialog(session))
        }
    }
}

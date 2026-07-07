package com.rumpilstilstkin.gloomhavenhelper.screens.start.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.GetCharactersForCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.classes.AddCharacterClassForTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.classes.GetAvaliableClassesForCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.classes.RemoveCharacterClassForTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreen.CharacterDetails
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent.Screen
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.dialogs.add.AddCharacterDialogContract
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.dialogs.delete.DeleteCharacterDialogContract
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.dialogs.menu.MenuCharacterDialogContract
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.dialogs.menu.MenuCharacterResult
import com.rumpilstilstkin.gloomhavenhelper.screens.core.ScreenEffect
import com.rumpilstilstkin.gloomhavenhelper.screens.core.createOverlaySession
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassTypeUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassTypeUI.Companion.toCharacterClassTypeUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersTabViewModel @Inject constructor(
    getCharactersForCurrentTeamUseCase: GetCharactersForCurrentTeamUseCase,
    getAvaliableClassesForCurrentTeamUseCase: GetAvaliableClassesForCurrentTeamUseCase,
    private val removeCharacterClassForTeamUseCase: RemoveCharacterClassForTeamUseCase,
    private val addCharacterClassForTeamUseCase: AddCharacterClassForTeamUseCase,
) : ViewModel() {
    private val _screenEvents = Channel<ScreenEffect>(Channel.BUFFERED)
    val screenEvents = _screenEvents.receiveAsFlow()

    private val charactersList: StateFlow<List<CharacterInfo>> =
        getCharactersForCurrentTeamUseCase()
            .stateIn(
                scope = viewModelScope,
                initialValue = emptyList(),
                started = SharingStarted.WhileSubscribed(5000),
            )

    private val avaliableClasses: StateFlow<List<CharacterClassTypeUI>> =
        getAvaliableClassesForCurrentTeamUseCase()
            .map { avaliableClasses -> avaliableClasses.map { it.toCharacterClassTypeUI() } }
            .stateIn(
                scope = viewModelScope,
                initialValue = emptyList(),
                started = SharingStarted.WhileSubscribed(5000),
            )

    private val logicState = MutableStateFlow(CharactersTabStateLogic())

    val uiState: StateFlow<CharactersTabStateUi> =
        combine(
            charactersList,
            logicState,
            avaliableClasses,
        ) { characters, logicState, classes ->
            val alive = characters.filter { it.isAlive }
            CharactersTabStateUi(
                filterAlive = logicState.filterAlive,
                characters =
                    (if (logicState.filterAlive) alive else characters)
                        .sortedBy { it.id }
                        .sortedBy { !it.isAlive }
                        .map { it.toUi() }
                        .toImmutableList(),
                canAdd = alive.size < 4,
                avaliableClasses = classes.toImmutableList(),
            )
        }.stateIn(
            scope = viewModelScope,
            initialValue = CharactersTabStateUi(),
            started = SharingStarted.WhileSubscribed(5000),
        )

    fun onAction(action: CharactersTabAction) {
        viewModelScope.launch {
            when (action) {
                is CharactersTabAction.SwitchAlive -> {
                    logicState.update { it.copy(filterAlive = !logicState.value.filterAlive) }
                }

                is CharactersTabAction.ShowAddCharacterDialog -> {
                    val session =
                        createOverlaySession(
                            contract = AddCharacterDialogContract,
                            input = Unit,
                            onResult = {},
                        )
                    _screenEvents.send(ScreenEffect.OpenDialog(session))
                }

                is CharactersTabAction.SwitchClassAvailability -> {
                    if (uiState.value.avaliableClasses.contains(action.type)) {
                        removeCharacterClassForTeamUseCase(action.type.type)
                    } else {
                        addCharacterClassForTeamUseCase(action.type.type)
                    }
                }

                is CharactersTabAction.CharacterMenu -> {
                    val session =
                        createOverlaySession(
                            contract = MenuCharacterDialogContract,
                            input = action.character,
                            onResult = { result ->
                                when (result) {
                                    is MenuCharacterResult.OpenCharacterDetails -> {
                                        viewModelScope.launch {
                                            _screenEvents.send(
                                                ScreenEffect.Navigation(
                                                    Screen(CharacterDetails(characterId = result.character.id)),
                                                ),
                                            )
                                        }
                                    }

                                    is MenuCharacterResult.DeleteCharacterRequest -> {
                                        showDeleteCharacterDialog(result.character)
                                    }

                                    else -> {}
                                }
                            },
                        )
                    _screenEvents.send(ScreenEffect.OpenBottomSheet(session))
                }
            }
        }
    }

    private fun showDeleteCharacterDialog(character: CharacterUI) {
        viewModelScope.launch {
            val session =
                createOverlaySession(
                    contract = DeleteCharacterDialogContract,
                    input = character,
                    onResult = { result ->
                        result?.let {
                            viewModelScope.launch {
                                _screenEvents.send(ScreenEffect.Message(R.string.character_deleted_toast))
                            }
                        }
                    },
                )
            _screenEvents.send(ScreenEffect.OpenDialog(session))
        }
    }
}

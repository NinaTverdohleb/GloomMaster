package com.rumpilstilstkin.gloomhavenhelper.screens.start.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.AddCharacterUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.GetCharactersForCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreens
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent.Screen
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersTabViewModel @Inject constructor(
    getCharactersForCurrentTeamUseCase: GetCharactersForCurrentTeamUseCase,
    private val addCharacterUseCase: AddCharacterUseCase,
) : ViewModel() {

    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    private val charactersList: StateFlow<List<CharacterInfo>> =
        getCharactersForCurrentTeamUseCase()
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
        ) { characters, logicState ->
            if (characters.isNotEmpty()) {
                val alive = characters.filter { it.isAlive }
                CharactersTabStateUi(
                    showAddCharacterDialog = logicState.showAddCharacterDialog,
                    filterAlive = logicState.filterAlive,
                    characters = (if (logicState.filterAlive) alive else characters)
                        .map { it.toUi() }
                        .toImmutableList(),
                    canAdd = alive.size < 4
                )
            } else {
                CharactersTabStateUi(showAddCharacterDialog = logicState.showAddCharacterDialog)
            }
        }.stateIn(
            scope = viewModelScope,
            initialValue = CharactersTabStateUi(),
            started = SharingStarted.WhileSubscribed(5000),
        )

    fun onAction(action: CharactersTabAction) {
        viewModelScope.launch {
            when (action) {
                is CharactersTabAction.AddCharacter -> {
                    logicState.emit(logicState.value.copy(showAddCharacterDialog = false))
                    addCharacterUseCase(
                        name = action.name,
                        level = action.level,
                        characterType = action.characterType
                    )
                }

                is CharactersTabAction.SwitchAlive -> {
                    logicState.emit(logicState.value.copy(filterAlive = !logicState.value.filterAlive))
                }

                is CharactersTabAction.ShowAddCharacterDialog -> {
                    logicState.emit(logicState.value.copy(showAddCharacterDialog = true))
                }

                is CharactersTabAction.CloseAddCharacterDialog -> {
                    logicState.emit(logicState.value.copy(showAddCharacterDialog = false))
                }

                is CharactersTabAction.CharacterDetails -> {
                    _navigationEvents.emit(Screen(GlHelperScreens.CharacterDetails(characterId = action.characterId)))
                }
            }
        }
    }
}
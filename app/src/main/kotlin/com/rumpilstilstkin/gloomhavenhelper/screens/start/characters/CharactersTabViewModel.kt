package com.rumpilstilstkin.gloomhavenhelper.screens.start.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.CreateCharacterUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.GetCharactersForCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.UpdateCharacterLevelUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.classes.AddCharacterClassForTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.classes.GetAvaliableClassesForCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.classes.RemoveCharacterClassForTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreens.*
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent.Screen
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassTypeUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassTypeUI.Companion.toCharacterClassTypeUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.map

@HiltViewModel
class CharactersTabViewModel @Inject constructor(
    getCharactersForCurrentTeamUseCase: GetCharactersForCurrentTeamUseCase,
    getAvaliableClassesForCurrentTeamUseCase: GetAvaliableClassesForCurrentTeamUseCase,
    private val createCharacterUseCase: CreateCharacterUseCase,
    private val removeCharacterClassForTeamUseCase: RemoveCharacterClassForTeamUseCase,
    private val addCharacterClassForTeamUseCase: AddCharacterClassForTeamUseCase,
    private val updateCharacterLevelUseCase: UpdateCharacterLevelUseCase
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
                showAddCharacterDialog = logicState.showAddCharacterDialog,
                filterAlive = logicState.filterAlive,
                characters = (if (logicState.filterAlive) alive else characters)
                    .map { it.toUi() }
                    .toImmutableList(),
                canAdd = alive.size < 4,
                avaliableClasses = classes.toImmutableList()
            )
        }.stateIn(
            scope = viewModelScope,
            initialValue = CharactersTabStateUi(),
            started = SharingStarted.WhileSubscribed(5000),
        )

    fun onAction(action: CharactersTabAction) {
        viewModelScope.launch {
            when (action) {
                is CharactersTabAction.AddCharacter -> {
                    logicState.update { it.copy(showAddCharacterDialog = false) }
                    createCharacterUseCase(
                        name = action.name,
                        level = action.level,
                        characterType = action.characterType.type
                    )
                }

                is CharactersTabAction.SwitchAlive -> {
                    logicState.update { it.copy(filterAlive = !logicState.value.filterAlive) }
                }

                is CharactersTabAction.ShowAddCharacterDialog -> {
                    logicState.update { it.copy(showAddCharacterDialog = true) }
                }

                is CharactersTabAction.CloseAddCharacterDialog -> {
                    logicState.update { it.copy(showAddCharacterDialog = false) }
                }

                is CharactersTabAction.CharacterDetails -> {
                    _navigationEvents.emit(Screen(CharacterDetails(characterId = action.characterId)))
                }

                is CharactersTabAction.SwitchClassAvailability -> {
                    if (uiState.value.avaliableClasses.contains(action.type)) {
                        removeCharacterClassForTeamUseCase(action.type.type)
                    } else {
                        addCharacterClassForTeamUseCase(action.type.type)
                    }
                }

                is CharactersTabAction.ChangeLevel -> {
                    updateCharacterLevelUseCase(action.characterId, action.level)
                }
            }
        }
    }
}
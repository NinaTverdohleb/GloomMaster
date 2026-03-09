package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.DeleteCharacterUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.GetCharacterGeneralInfoFlowUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.RetireCharacterUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.SetTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.UpdateCharacterLevelUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.characters.UpdateCharacterNameUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassTypeUI.Companion.toCharacterClassTypeUI
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CharacterDetailsViewModel.Factory::class)
class CharacterDetailsViewModel @AssistedInject constructor(
    @Assisted val id: Int,
    getCharacterUseCase: GetCharacterGeneralInfoFlowUseCase,
    private val setTeamUseCase: SetTeamUseCase,
    private val deleteCharacterUseCase: DeleteCharacterUseCase,
    private val updateCharacterNameUseCase: UpdateCharacterNameUseCase,
    private val updateCharacterLevelUseCase: UpdateCharacterLevelUseCase,
    private val retireCharacterUseCase: RetireCharacterUseCase,
) : ViewModel() {

    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    private val logicState = MutableStateFlow(CharacterDetailsStateLogic())

    val uiState: StateFlow<CharacterDetailsStateUi> =
        combine(
            getCharacterUseCase(id),
            logicState
        ) { character, logic ->
            CharacterDetailsStateUi(
                level = character.level,
                name = character.name,
                teamName = character.team?.name ?: "",
                type = character.characterType.toCharacterClassTypeUI(),
                showDeleteDialog = logic.showDeleteDialog,
                showNameDialog = logic.showNameDialog,
                showChangeLevelDialog = logic.showChangeLevelDialog,
                isActive = character.isAlive
            )
        }.stateIn(
            scope = viewModelScope,
            initialValue = CharacterDetailsStateUi(),
            started = SharingStarted.WhileSubscribed(100),
        )

    @AssistedFactory
    interface Factory {
        fun create(id: Int): CharacterDetailsViewModel
    }

    fun onAction(action: CharacterDetailsAction) {
        viewModelScope.launch {
            when (action) {
                is CharacterDetailsAction.Back -> {
                    _navigationEvents.emit(GlHelperEvent.Back)
                }

                is CharacterDetailsAction.ShowDeleteDialog -> {
                    logicState.update { it.copy(showDeleteDialog = true) }
                }

                is CharacterDetailsAction.HideDeleteDialog -> {
                    logicState.update { it.copy(showDeleteDialog = false) }
                }

                is CharacterDetailsAction.ConfirmDelete -> {
                    deleteCharacterUseCase(id)
                    logicState.update { it.copy(showDeleteDialog = false) }
                    _navigationEvents.emit(GlHelperEvent.Back)
                }

                is CharacterDetailsAction.ShowNameDialog -> {
                    logicState.update { it.copy(showNameDialog = true) }
                }

                is CharacterDetailsAction.HideNameDialog -> {
                    logicState.update { it.copy(showNameDialog = false) }
                }

                is CharacterDetailsAction.SaveName -> {
                    updateCharacterNameUseCase(id, action.name)
                    logicState.update { it.copy(showNameDialog = false) }
                }

                is CharacterDetailsAction.ChangeTeam -> {
                    setTeamUseCase(id, action.teamId)
                }

                is CharacterDetailsAction.ChangeLevel -> {
                    updateCharacterLevelUseCase(id, action.level)
                    logicState.update { it.copy(showChangeLevelDialog = false) }
                }

                CharacterDetailsAction.HideChangeLevelDialog -> {
                    logicState.update { it.copy(showChangeLevelDialog = false) }
                }

                CharacterDetailsAction.ShowChangeLevelDialog -> {
                    logicState.update { it.copy(showChangeLevelDialog = true) }
                }

                CharacterDetailsAction.Retire -> {
                    logicState.update { it.copy(showDeleteDialog = false) }
                    retireCharacterUseCase(id)
                }
            }
        }
    }
}
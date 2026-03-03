package com.rumpilstilstkin.gloomhavenhelper.screens.teem.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.data.ClassRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.SaveTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamCreateViewModel@Inject constructor(
    private val classRepository: ClassRepository,
    private val saveTeamUseCase: SaveTeamUseCase
) : ViewModel() {
    private val team = MutableStateFlow(TeamCreateUiState.Empty)

    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    val uiState: StateFlow<TeamCreateUiState> = team.asStateFlow()

    init {
        viewModelScope.launch {
            val classes = classRepository.getAllClasses().map {it.toUi()}
            team.emit(TeamCreateUiState.Empty.copy(classes = classes))
        }
    }

    fun action(action: TeamCreateAction) {
        val currentValue = team.value
        viewModelScope.launch {
            when (action) {
                is TeamCreateAction.EditName -> {
                    team.emit(currentValue.copy(name = action.name))
                }

                is TeamCreateAction.AddCharacter -> {
                    classRepository.getClassByType(action.characterType).let { classModel ->
                        val newCharacterList = currentValue.characters + CharacterUI(
                            id = currentValue.characters.size + 1,
                            name = action.name,
                            level = action.level,
                            characterClass = classModel.toUi(),
                            teamName = team.value.name
                        )
                        val newValue = currentValue.copy(
                            characters = newCharacterList,
                            showCharacterDialog = false,
                            canAdd = newCharacterList.size < 5
                        )
                        team.emit(newValue)
                    }
                }

                is TeamCreateAction.Save -> {
                    saveTeamUseCase(currentValue.toTeamForSave())
                    _navigationEvents.emit(GlHelperEvent.Back)
                }

                is TeamCreateAction.ShowCharacterDialog -> {
                    team.emit(currentValue.copy(showCharacterDialog = true))
                }

                TeamCreateAction.HideCharacterDialog -> {
                    team.emit(currentValue.copy(showCharacterDialog = false))
                }

                is TeamCreateAction.DeleteCharacter -> {
                    val newCharacterList = currentValue.characters.filter { it.id != action.id }
                    val newValue = currentValue.copy(
                        characters = newCharacterList,
                        canAdd = newCharacterList.size < 5
                    )
                    team.emit(newValue)
                }
                is TeamCreateAction.LeaveCharacter -> {
                    val newCharacterList = currentValue.characters.map {
                        if (it.id == action.id) {
                            it.copy(isAlive = false)
                        } else {
                            it
                        }
                    }
                    val newValue = currentValue.copy(
                        characters = newCharacterList,
                        canAdd = newCharacterList.size < 5
                    )
                    team.emit(newValue)
                }
                is TeamCreateAction.UpdateCharacter -> {
                    val newCharacterList = currentValue.characters.map {
                        if (it.id == action.id) {
                            it.copy(level = action.level)
                        } else {
                            it
                        }
                    }
                    team.emit(currentValue.copy(characters = newCharacterList))
                }
            }
        }
    }
}
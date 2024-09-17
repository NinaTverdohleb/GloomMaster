package com.rumpilstilstkin.gloomhavenhelper.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.data.CharacterRepository
import com.rumpilstilstkin.gloomhavenhelper.data.ClassRepository
import com.rumpilstilstkin.gloomhavenhelper.data.LevelInfoRepository
import com.rumpilstilstkin.gloomhavenhelper.data.TeamRepository
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterForSave
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.LevelInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.CompleteScenarioUsecase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.GetCurrentTeamUsecase
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.TeamUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getCurrentTeamUsecase: GetCurrentTeamUsecase,
    levelInfoRepository: LevelInfoRepository,
    classRepository: ClassRepository,
    private val teamRepository: TeamRepository,
    private val characterRepository: CharacterRepository,
    private val completeScenarioUsecase: CompleteScenarioUsecase
) : ViewModel() {
    private val _effects = MutableStateFlow(MainScreenEffect())
    val effects: StateFlow<MainScreenEffect> = _effects.asStateFlow()

    val uiState: StateFlow<MainScreenUiState> = getCurrentTeamUsecase.invoke().map { team ->
        _effects.emit(
            _effects.value.copy(
                levelInfo = levelInfoRepository.getLevelInfo(team.level).getOrNull(),
                reputation = team.reputation,
                prosperity = team.prosperity
            )
        )
        MainScreenUiState.Team(
            team = TeamUI(
                teamId = team.id,
                teamName = team.name,
                teamLevel = team.level,
                teamScenario = team.scenario.map { it.toUI() },
                teamReputation = team.reputation,
                prosperity = team.prosperity,
                teamAchievements = team.teamAchievement,
                globalAchievements = team.globalAchievement,
                characters = team.characters.map { it.toUI() },
                canAddCharacter = team.characters.size < 4
            ),
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainScreenUiState.Empty,
        started = SharingStarted.WhileSubscribed(10),
    )

    init {
        viewModelScope.launch {
            val classes = classRepository.getAllClasses().map { classModel ->
                CharacterClassUI(
                    id = classModel.id,
                    name = classModel.name,
                    imageRes = classModel.image
                )
            }
            _effects.emit(_effects.value.copy(classes = classes))
        }
    }

    fun action(action: MainScreenAction) {
        when (action) {
            is MainScreenAction.ShowLevelInfoDialog -> {
                if (_effects.value.levelInfo != null) {
                    viewModelScope.launch {
                        _effects.emit(_effects.value.copy(showLevelInfoDialog = true))
                    }
                }
            }

            is MainScreenAction.HideLevelInfoDialog -> {
                viewModelScope.launch {
                    _effects.emit(_effects.value.copy(showLevelInfoDialog = false))
                }
            }

            is MainScreenAction.ShowProsperityDialog -> {
                viewModelScope.launch {
                    _effects.emit(_effects.value.copy(showProsperityDialog = true))
                }
            }

            is MainScreenAction.HideProsperityDialog -> {
                viewModelScope.launch {
                    _effects.emit(_effects.value.copy(showProsperityDialog = false))
                }
            }

            is MainScreenAction.ShowReputationDialog -> {
                viewModelScope.launch {
                    _effects.emit(_effects.value.copy(showReputationDialog = true))
                }
            }

            is MainScreenAction.HideReputationDialog -> {
                viewModelScope.launch {
                    _effects.emit(_effects.value.copy(showReputationDialog = false))
                }
            }

            is MainScreenAction.SetNewReputation -> {
                viewModelScope.launch {
                    teamRepository.updateReputation(action.reputation)
                    _effects.emit(_effects.value.copy(showReputationDialog = false))
                }
            }

            is MainScreenAction.SetNewProsperity -> {
                viewModelScope.launch {
                    teamRepository.updateProsperity(action.prosperity)
                    _effects.emit(_effects.value.copy(showProsperityDialog = false))
                }
            }

            is MainScreenAction.ShowAddCharacterDialog -> {
                viewModelScope.launch {
                    _effects.emit(_effects.value.copy(showAddCharacterDialog = true))
                }
            }

            is MainScreenAction.HideAddCharacterDialog -> {
                viewModelScope.launch {
                    _effects.emit(_effects.value.copy(showAddCharacterDialog = false))
                }
            }

            is MainScreenAction.AddCharacter -> {
                viewModelScope.launch {
                    teamRepository.addCharacterForCurrentTeam(
                        CharacterForSave(
                            name = action.name,
                            level = action.level,
                            classId = action.classId
                        )
                    )
                    _effects.emit(_effects.value.copy(showAddCharacterDialog = false))
                }
            }

            is MainScreenAction.DeleteCharacter -> {
                viewModelScope.launch {
                    characterRepository.deleteCharacter(action.id)
                }
            }

            is MainScreenAction.EditCharacter -> {
                viewModelScope.launch {
                    characterRepository.updateLevel(id = action.id, level = action.level)
                }
            }

            is MainScreenAction.LeaveCharacter -> {
                viewModelScope.launch {
                    characterRepository.leaveCharacter(action.id)
                }
            }

            is MainScreenAction.CompleteScenario -> {
                viewModelScope.launch {
                    completeScenarioUsecase.invoke(action.scenarioNumber)
                }
            }
        }
    }
}

sealed interface MainScreenUiState {
    data object Empty : MainScreenUiState
    data class Team(
        val team: TeamUI,
    ) : MainScreenUiState
}

data class MainScreenEffect(
    val classes: List<CharacterClassUI> = emptyList(),
    val levelInfo: LevelInfo? = null,
    val reputation: Int = 0,
    val prosperity: Int = 0,
    val showLevelInfoDialog: Boolean = false,
    val showProsperityDialog: Boolean = false,
    val showReputationDialog: Boolean = false,
    val showAddCharacterDialog: Boolean = false,
    val showAddScenarioDialog: Boolean = false,
)
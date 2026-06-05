package com.rumpilstilstkin.gloomhavenhelper.screens.teem.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.selectablePacks
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.DeleteCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetCurrentTeamWithTeamsUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.SwitchPackForCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.UpdateDifficultyLevelUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.UpdateNameForCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamEditViewModel @Inject constructor(
    getCurrentTeamWithTeamsCountUseCase: GetCurrentTeamWithTeamsUseCase,
    private val switchPackForCurrentTeamUseCase: SwitchPackForCurrentTeamUseCase,
    private val updateNameForCurrentTeamUseCase: UpdateNameForCurrentTeamUseCase,
    private val deleteCurrentTeamUseCase: DeleteCurrentTeamUseCase,
    private val updateDifficultyLevelUseCase: UpdateDifficultyLevelUseCase,
) : ViewModel() {
    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    private val logicState: MutableStateFlow<TeamEditStateLogic> =
        MutableStateFlow(TeamEditStateLogic())

    val uiState: StateFlow<TeamEditStateUi> =
        combine(
            getCurrentTeamWithTeamsCountUseCase(),
            logicState,
        ) { (team, teams), logicState ->
            if (team == null) {
                TeamEditStateUi()
            } else {
                val currentPacks = team.packs.toSet()

                TeamEditStateUi(
                    teamName = team.name,
                    difficultyLevel = team.difficultyLevel,
                    availablePacks =
                        selectablePacks
                            .map { packType ->
                                PackItemUi(
                                    pack = packType,
                                    isEnabled = packType in currentPacks,
                                )
                            }.toImmutableList(),
                    showDeleteConfirmDialog = logicState.showDeleteConfirmDialog,
                )
            }
        }.stateIn(
            scope = viewModelScope,
            initialValue = TeamEditStateUi(),
            started = SharingStarted.WhileSubscribed(5000),
        )

    fun onAction(action: TeamEditAction) {
        viewModelScope.launch {
            when (action) {
                is TeamEditAction.ChangeTeamName -> {
                    updateNameForCurrentTeamUseCase(action.name)
                }

                is TeamEditAction.TogglePack -> {
                    switchPackForCurrentTeamUseCase(action.pack)
                }

                is TeamEditAction.Back -> {
                    _navigationEvents.emit(GlHelperEvent.Back)
                }

                is TeamEditAction.ShowDeleteConfirmDialog -> {
                    logicState.update { it.copy(showDeleteConfirmDialog = true) }
                }

                is TeamEditAction.DismissDeleteConfirmDialog -> {
                    logicState.update { it.copy(showDeleteConfirmDialog = false) }
                }

                is TeamEditAction.ConfirmDelete -> {
                    logicState.update { it.copy(showDeleteConfirmDialog = false) }
                    deleteCurrentTeamUseCase()
                    _navigationEvents.emit(GlHelperEvent.Back)
                }

                is TeamEditAction.ChangeDifficultyLevel -> {
                    updateDifficultyLevelUseCase(action.level)
                }
            }
        }
    }
}

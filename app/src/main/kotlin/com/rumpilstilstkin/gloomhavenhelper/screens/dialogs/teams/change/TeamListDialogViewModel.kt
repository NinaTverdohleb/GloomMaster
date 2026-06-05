package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.teams.change

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.ChangeCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetCurrentTeamWithTeamsUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortTeamInfoUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamListDialogViewModel @Inject constructor(
    getCurrentTeamWithTeamsCountUseCase: GetCurrentTeamWithTeamsUseCase,
    private val changeCurrentTeamUseCase: ChangeCurrentTeamUseCase,
) : ViewModel() {
    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    val uiState: StateFlow<TeamListDialogState> =
        getCurrentTeamWithTeamsCountUseCase()
            .map { (team, teams) ->
                TeamListDialogState(
                    teams =
                        teams
                            .map {
                                ShortTeamInfoUi(
                                    teamId = it.teamId,
                                    teamName = it.name,
                                    level = it.difficultyLevel,
                                )
                            }.toImmutableList(),
                )
            }.stateIn(
                scope = viewModelScope,
                initialValue = TeamListDialogState(persistentListOf()),
                started = SharingStarted.WhileSubscribed(5000),
            )

    fun onAction(action: TeamListDialogAction) {
        viewModelScope.launch {
            when (action) {
                TeamListDialogAction.Back -> {
                    _navigationEvents.emit(GlHelperEvent.Back)
                }

                is TeamListDialogAction.SelectTeam -> {
                    changeCurrentTeamUseCase(action.teamId)
                    _navigationEvents.emit(GlHelperEvent.Back)
                }
            }
        }
    }
}

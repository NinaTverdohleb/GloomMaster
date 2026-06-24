package com.rumpilstilstkin.gloomhavenhelper.screens.teem.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetCurrentTeamWithTeamsUseCase
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortTeamInfoUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamListDialogViewModel @Inject constructor(
    getCurrentTeamWithTeamsCountUseCase: GetCurrentTeamWithTeamsUseCase,
) : ViewModel() {
    private val _complete = Channel<TeamListDialogComplete>(Channel.BUFFERED)
    val complete = _complete.receiveAsFlow()

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
        when (action) {
            is TeamListDialogAction.SelectTeam -> {
                viewModelScope.launch {
                    _complete.send(TeamListDialogComplete(action.team))
                }
            }
        }
    }
}

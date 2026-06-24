package com.rumpilstilstkin.gloomhavenhelper.screens.teem.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.ChangeCurrentTeamUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TeamMenuDialogViewModel @Inject constructor(
    private val changeCurrentTeamUseCase: ChangeCurrentTeamUseCase,
) : ViewModel() {
    private val _complete = Channel<TeamMenuDialogComplete>(Channel.BUFFERED)
    val complete = _complete.receiveAsFlow()

    fun onAction(action: TeamMenuDialogAction) {
        viewModelScope.launch {
            when (action) {
                is TeamMenuDialogAction.SelectTeam -> {
                    changeCurrentTeamUseCase(action.team.teamId)
                    _complete.send(TeamMenuDialogComplete(TeamMenuResult.NewTeamSelected))
                }

                is TeamMenuDialogAction.DeleteTeam -> {
                    _complete.send(TeamMenuDialogComplete(TeamMenuResult.DeleteTeamRequest(action.team)))
                }
            }
        }
    }
}

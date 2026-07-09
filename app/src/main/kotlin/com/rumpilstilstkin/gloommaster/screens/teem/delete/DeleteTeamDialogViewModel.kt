package com.rumpilstilstkin.gloommaster.screens.teem.delete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloommaster.domain.usecase.team.DeleteTeamUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DeleteTeamDialogViewModel @Inject constructor(
    private val deleteTeamUseCase: DeleteTeamUseCase,
) : ViewModel() {
    private val _complete = Channel<DeleteTeamDialogComplete>(Channel.BUFFERED)
    val complete = _complete.receiveAsFlow()

    fun onAction(action: DeleteTeamDialogAction) {
        viewModelScope.launch {
            when (action) {
                is DeleteTeamDialogAction.DeleteTeam -> {
                    deleteTeamUseCase(action.teamId)
                    _complete.send(DeleteTeamDialogComplete)
                }
            }
        }
    }
}

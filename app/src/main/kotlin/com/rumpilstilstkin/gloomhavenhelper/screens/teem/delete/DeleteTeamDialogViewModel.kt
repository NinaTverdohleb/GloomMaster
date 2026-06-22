package com.rumpilstilstkin.gloomhavenhelper.screens.teem.delete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.DeleteTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = DeleteTeamDialogViewModel.Factory::class)
class DeleteTeamDialogViewModel @AssistedInject constructor(
    private val deleteTeamUseCase: DeleteTeamUseCase,
    @Assisted private val id: Int,
    @Assisted private val name: String,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: Int, name: String): DeleteTeamDialogViewModel
    }

    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    val uiState: String = name

    fun onAction(action: DeleteTeamDialogAction) {
        viewModelScope.launch {
            when (action) {
                DeleteTeamDialogAction.Back -> {
                    _navigationEvents.emit(GlHelperEvent.Back)
                }

                is DeleteTeamDialogAction.DeleteTeam -> {
                    deleteTeamUseCase(id)
                    _navigationEvents.emit(GlHelperEvent.Back)
                }
            }
        }
    }
}

sealed interface DeleteTeamDialogAction {
    object Back : DeleteTeamDialogAction
    object DeleteTeam : DeleteTeamDialogAction
}

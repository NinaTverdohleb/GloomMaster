package com.rumpilstilstkin.gloomhavenhelper.screens.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreen
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor(
    getCurrentTeamUseCase: GetCurrentTeamUseCase,
) : ViewModel() {
    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    val uiState: StateFlow<StartScreenState> =
        getCurrentTeamUseCase()
            .map { team ->
                if (team == null) {
                    StartScreenState.Empty
                } else {
                    StartScreenState.Team(
                        name = team.name,
                        id = team.id,
                    )
                }
            }.stateIn(
                scope = viewModelScope,
                initialValue = StartScreenState.Empty,
                started = SharingStarted.WhileSubscribed(5000),
            )

    fun onAction(action: StartScreenAction) {
        viewModelScope.launch {
            when (action) {
                StartScreenAction.AddTeam -> {}
                StartScreenAction.Settings -> _navigationEvents.emit(Screen(GlHelperScreen.Settings))
            }
        }
    }
}

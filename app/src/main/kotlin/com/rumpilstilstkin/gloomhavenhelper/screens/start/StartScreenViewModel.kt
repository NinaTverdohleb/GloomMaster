package com.rumpilstilstkin.gloomhavenhelper.screens.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.CompleteScenarioUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.ChangeCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreens
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreens.CharacterDetails
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreens.Scenario
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent.Screen
import com.rumpilstilstkin.gloomhavenhelper.screens.models.TeamUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toUi
import com.rumpilstilstkin.gloomhavenhelper.screens.start.team.TeamTabAction
import com.rumpilstilstkin.gloomhavenhelper.screens.start.team.TeamTabUiState
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
    val changeCurrentTeamUseCase: ChangeCurrentTeamUseCase
) : ViewModel() {

    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    val uiState: StateFlow<StartScreenState> = getCurrentTeamUseCase.invoke().map { team ->
        StartScreenState.Team(
            name = team.name,
            id = team.id
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = StartScreenState.Empty,
        started = SharingStarted.WhileSubscribed(10),
    )

    fun onAction(action: StartScreenAction) {
        viewModelScope.launch {
            when (action) {
                StartScreenAction.CreateTeam -> {
                    _navigationEvents.emit(Screen(GlHelperScreens.TeamCreate))
                }

                is StartScreenAction.SelectTeam -> {
                    changeCurrentTeamUseCase(action.teamId)
                }
            }
        }
    }
}

sealed interface StartScreenAction {
    data object CreateTeam : StartScreenAction
    data class SelectTeam(val teamId: Int) : StartScreenAction
}
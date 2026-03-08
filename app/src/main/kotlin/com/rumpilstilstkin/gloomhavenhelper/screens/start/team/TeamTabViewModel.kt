package com.rumpilstilstkin.gloomhavenhelper.screens.start.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.CompleteScenarioUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.UpdateTeamProsperityUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.UpdateTeamReputationUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreens
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreens.Scenario
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent.Screen
import com.rumpilstilstkin.gloomhavenhelper.screens.models.TeamUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
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
class TeamTabViewModel @Inject constructor(
    getCurrentTeamUseCase: GetCurrentTeamUseCase,
    private val completeScenarioUseCase: CompleteScenarioUseCase,
    private val updateTeamProsperityUseCase: UpdateTeamProsperityUseCase,
    private val updateTeamReputationUseCase: UpdateTeamReputationUseCase,
) : ViewModel() {

    private val _navigationEvents = MutableSharedFlow<GlHelperEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    val uiState: StateFlow<TeamTabUiState> = getCurrentTeamUseCase().map { team ->
        if (team == null) {
            TeamTabUiState.Empty
        } else {
            TeamTabUiState.Data(
                currentTeam = TeamUI(
                    teamId = team.id,
                    teamName = team.name,
                    teamLevel = team.level,
                    teamScenario = team.activeScenario.map { it.toUi() }.toImmutableList(),
                    teamReputation = team.reputation,
                    prosperity = team.prosperity,
                    teamAchievements = team.teamAchievement,
                    globalAchievements = team.globalAchievement,
                    characters = team.characters.map { it.toUi() }.toImmutableList(),
                    canAddCharacter = team.characters.size < 4,
                    shopDiscount = team.shopDiscount
                ),
            )
        }
    }.stateIn(
        scope = viewModelScope,
        initialValue = TeamTabUiState.Empty,
        started = SharingStarted.WhileSubscribed(10),
    )

    fun onAction(action: TeamTabAction) {
        val state = uiState.value as? TeamTabUiState.Data ?: return
        viewModelScope.launch {
            when (action) {
                is TeamTabAction.UpdateProsperity -> {
                    updateTeamProsperityUseCase(
                        prosperity = state.currentTeam.prosperity,
                        newProsperityLevelValue = action.value
                    )
                }

                is TeamTabAction.UpdateReputation -> {
                    if (state.currentTeam.teamReputation != action.value) {
                        updateTeamReputationUseCase(action.value)
                    }
                }

                is TeamTabAction.StartScenario -> {
                    _navigationEvents.emit(Screen(Scenario(scenarioId = action.scenarioId)))
                }

                is TeamTabAction.CompleteScenario -> {
                    completeScenarioUseCase.invoke(scenarioNumber = action.scenarioId)
                }

                TeamTabAction.AddScenario -> {
                    _navigationEvents.emit(Screen(GlHelperScreens.AddScenarioForTeam))
                }
            }
        }
    }
}

sealed interface TeamTabAction {
    data class StartScenario(val scenarioId: Int) : TeamTabAction
    data class CompleteScenario(val scenarioId: Int) : TeamTabAction
    data class UpdateReputation(val value: Int) : TeamTabAction
    data class UpdateProsperity(val value: Int) : TeamTabAction
    data object AddScenario : TeamTabAction
}
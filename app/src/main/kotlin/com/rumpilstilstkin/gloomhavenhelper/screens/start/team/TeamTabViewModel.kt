package com.rumpilstilstkin.gloomhavenhelper.screens.start.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.DonateUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.CompleteScenarioUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.CreateActiveScenarioUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.DeleteScenarioUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetNextChurchValueUseCase
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
    private val createActiveScenarioUseCase: CreateActiveScenarioUseCase,
    private val donateUseCase: DonateUseCase,
    private val getNextChurchValueUseCase: GetNextChurchValueUseCase,
    private val deleteScenarioUseCase: DeleteScenarioUseCase,
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
                    teamAchievements = team.teamAchievement.toImmutableList(),
                    globalAchievements = team.globalAchievement.toImmutableList(),
                    characters = team.aliveCharacters.map { it.toUi() }.toImmutableList(),
                    canAddCharacter = team.aliveCharacters.size < 4,
                    shopDiscount = team.shopDiscount,
                    hasActiveScenario = team.hasActiveScenario,
                    churchValue = team.churchValue,
                    churchValueForNextProsperity = getNextChurchValueUseCase(churchValue = team.churchValue)
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
                        newProsperityLevelValue = action.value
                    )
                }

                is TeamTabAction.UpdateReputation -> {
                    if (state.currentTeam.teamReputation != action.value) {
                        updateTeamReputationUseCase(action.value)
                    }
                }

                is TeamTabAction.StartScenario -> {
                    createActiveScenarioUseCase(action.scenarioId).onSuccess {
                        _navigationEvents.emit(Screen(Scenario))
                    }
                }

                is TeamTabAction.CompleteScenario -> {
                    completeScenarioUseCase.invoke(scenarioNumber = action.scenarioId)
                }

                TeamTabAction.OpenGlobalAchievements -> {
                    _navigationEvents.emit(Screen(GlHelperScreens.GlobalAchievements))
                }

                TeamTabAction.OpenTeamAchievements -> {
                    _navigationEvents.emit(Screen(GlHelperScreens.TeamAchievements))
                }

                TeamTabAction.RestoreLastScenario -> {
                    _navigationEvents.emit(Screen(Scenario))
                }

                TeamTabAction.Donate -> donateUseCase(
                    oldChurchValue = state.currentTeam.churchValue
                )

                is TeamTabAction.DeleteScenario -> deleteScenarioUseCase(action.scenarioNumber)
            }
        }
    }
}

sealed interface TeamTabAction {
    data class StartScenario(val scenarioId: Int?) : TeamTabAction
    data class CompleteScenario(val scenarioId: Int) : TeamTabAction
    data class UpdateReputation(val value: Int) : TeamTabAction
    data class UpdateProsperity(val value: Int) : TeamTabAction
    data object OpenTeamAchievements : TeamTabAction
    data object OpenGlobalAchievements : TeamTabAction
    data object RestoreLastScenario : TeamTabAction
    data object Donate : TeamTabAction
    data class DeleteScenario(val scenarioNumber: Int): TeamTabAction
}
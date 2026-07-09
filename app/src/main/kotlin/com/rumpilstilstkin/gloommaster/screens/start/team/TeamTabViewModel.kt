package com.rumpilstilstkin.gloommaster.screens.start.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloommaster.domain.entity.AchievementWithName
import com.rumpilstilstkin.gloommaster.domain.usecase.scenario.CreateActiveScenarioUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.team.DonateUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.team.GetCurrentTeamUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.team.GetNextChurchValueUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.team.UpdateTeamProsperityUseCase
import com.rumpilstilstkin.gloommaster.domain.usecase.team.UpdateTeamReputationUseCase
import com.rumpilstilstkin.gloommaster.navigation.GlHelperScreen
import com.rumpilstilstkin.gloommaster.navigation.GlHelperScreen.Scenario
import com.rumpilstilstkin.gloommaster.navigation.events.GlHelperEvent.Screen
import com.rumpilstilstkin.gloommaster.screens.core.ScreenEffect
import com.rumpilstilstkin.gloommaster.screens.core.createOverlaySession
import com.rumpilstilstkin.gloommaster.screens.models.ShortScenarioUI
import com.rumpilstilstkin.gloommaster.screens.models.TeamUI
import com.rumpilstilstkin.gloommaster.screens.models.toUi
import com.rumpilstilstkin.gloommaster.screens.scenario.dialog.delete.DeleteScenarioDialogContract
import com.rumpilstilstkin.gloommaster.screens.scenario.dialog.menu.MenuScenarioDialogContract
import com.rumpilstilstkin.gloommaster.screens.scenario.dialog.menu.MenuScenarioResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamTabViewModel @Inject constructor(
    getCurrentTeamUseCase: GetCurrentTeamUseCase,
    private val updateTeamProsperityUseCase: UpdateTeamProsperityUseCase,
    private val updateTeamReputationUseCase: UpdateTeamReputationUseCase,
    private val createActiveScenarioUseCase: CreateActiveScenarioUseCase,
    private val donateUseCase: DonateUseCase,
    private val getNextChurchValueUseCase: GetNextChurchValueUseCase,
) : ViewModel() {
    private val _screenEvents = Channel<ScreenEffect>(Channel.BUFFERED)
    val screenEvents = _screenEvents.receiveAsFlow()

    val uiState: StateFlow<TeamTabUiState> =
        getCurrentTeamUseCase()
            .distinctUntilChanged()
            .map { team ->
                if (team == null) {
                    TeamTabUiState.Empty
                } else {
                    TeamTabUiState.Data(
                        currentTeam =
                            TeamUI(
                                teamId = team.id,
                                teamName = team.name,
                                teamLevel = team.level,
                                teamScenario =
                                    team.activeScenario
                                        .map { it.toUi() }
                                        .toImmutableList(),
                                teamReputation = team.reputation,
                                prosperity = team.prosperity,
                                teamAchievements = team.teamAchievement.getAchievementsString(),
                                globalAchievements = team.globalAchievement.getAchievementsString(),
                                shopDiscount = team.shopDiscount,
                                hasActiveScenario = team.hasActiveScenario,
                                churchValue = team.churchValue,
                                churchValueForNextProsperity = getNextChurchValueUseCase(churchValue = team.churchValue),
                            ),
                    )
                }
            }.stateIn(
                scope = viewModelScope,
                initialValue = TeamTabUiState.Empty,
                started = SharingStarted.WhileSubscribed(5000),
            )

    fun onAction(action: TeamTabAction) {
        val state = uiState.value as? TeamTabUiState.Data ?: return
        viewModelScope.launch {
            when (action) {
                is TeamTabAction.UpdateProsperity -> {
                    updateTeamProsperityUseCase(
                        newProsperityLevelValue = action.value,
                    )
                }

                is TeamTabAction.UpdateReputation -> {
                    if (state.currentTeam.teamReputation != action.value) {
                        updateTeamReputationUseCase(action.value)
                    }
                }

                TeamTabAction.OpenGlobalAchievements -> {
                    _screenEvents.send(ScreenEffect.Navigation(Screen(GlHelperScreen.GlobalAchievements)))
                }

                TeamTabAction.OpenTeamAchievements -> {
                    _screenEvents.send(ScreenEffect.Navigation(Screen(GlHelperScreen.TeamAchievements)))
                }

                TeamTabAction.RestoreLastScenario -> {
                    _screenEvents.send(ScreenEffect.Navigation(Screen(Scenario)))
                }

                TeamTabAction.Donate -> {
                    donateUseCase(
                        oldChurchValue = state.currentTeam.churchValue,
                    )
                }

                is TeamTabAction.SelectScenario -> {
                    val session =
                        createOverlaySession(
                            contract = MenuScenarioDialogContract,
                            input = action.scenario,
                            onResult = { result ->
                                when (result) {
                                    is MenuScenarioResult.DeleteScenarioRequest -> {
                                        openDeleteScenarioDialog(result.scenario)
                                    }

                                    is MenuScenarioResult.PlayScenario -> {
                                        viewModelScope.launch {
                                            createActiveScenarioUseCase(action.scenario.scenarioNumber)
                                                .onSuccess {
                                                    _screenEvents.send(
                                                        ScreenEffect.Navigation(
                                                            (Screen(Scenario)),
                                                        ),
                                                    )
                                                }
                                        }
                                    }

                                    else -> {}
                                }
                            },
                        )
                    _screenEvents.send(ScreenEffect.OpenBottomSheet(session))
                }
            }
        }
    }

    private fun openDeleteScenarioDialog(scenario: ShortScenarioUI) {
        viewModelScope.launch {
            val session =
                createOverlaySession(
                    contract = DeleteScenarioDialogContract,
                    input = scenario,
                    onResult = { },
                )
            _screenEvents.send(ScreenEffect.OpenDialog(session))
        }
    }

    private fun List<AchievementWithName>.getAchievementsString(): String =
        joinToString(separator = ", ") { achievement ->
            if (achievement.maxValue > 1) {
                "${achievement.name} - ${achievement.value}"
            } else {
                achievement.name
            }
        }
}

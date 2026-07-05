package com.rumpilstilstkin.gloomhavenhelper.screens.start.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Achievement
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AchievementWithName
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.scenario.CreateActiveScenarioUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.DonateUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetCurrentTeamUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.GetNextChurchValueUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.UpdateTeamProsperityUseCase
import com.rumpilstilstkin.gloomhavenhelper.domain.usecase.team.UpdateTeamReputationUseCase
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreen
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreen.Scenario
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEvent.Screen
import com.rumpilstilstkin.gloomhavenhelper.screens.core.ScreenEffect
import com.rumpilstilstkin.gloomhavenhelper.screens.core.createOverlaySession
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.TeamUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toUi
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.dialog.delete.DeleteScenarioDialogContract
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.dialog.menu.MenuScenarioDialogContract
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.dialog.menu.MenuScenarioResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
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
    private val _screenEvents = MutableSharedFlow<ScreenEffect>()
    val screenEvents = _screenEvents.asSharedFlow()

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
                    _screenEvents.emit(ScreenEffect.Navigation(Screen(GlHelperScreen.GlobalAchievements)))
                }

                TeamTabAction.OpenTeamAchievements -> {
                    _screenEvents.emit(ScreenEffect.Navigation(Screen(GlHelperScreen.TeamAchievements)))
                }

                TeamTabAction.RestoreLastScenario -> {
                    _screenEvents.emit(ScreenEffect.Navigation(Screen(Scenario)))
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
                                                    _screenEvents.emit(
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
                    _screenEvents.emit(ScreenEffect.OpenBottomSheet(session))
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
            _screenEvents.emit(ScreenEffect.OpenDialog(session))
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

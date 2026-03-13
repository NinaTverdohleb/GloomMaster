package com.rumpilstilstkin.gloomhavenhelper.screens.start.team

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper

@Composable
fun TeamTabRoute(
    navController: NavHostController,
    viewModel: TeamTabViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navigationEvents by viewModel.navigationEvents.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(navigationEvents) {
        navigationEvents?.let { event ->
            GlHelperEventHelper.event(
                event = event,
                navController = navController
            )
        }
    }
    TeamTabScreen(
        state = uiState,
        completeScenario = { scenarioId ->
            viewModel.onAction(
                TeamTabAction.CompleteScenario(
                    scenarioId
                )
            )
        },
        startScenario = { scenarioId -> viewModel.onAction(TeamTabAction.StartScenario(scenarioId)) },
        updateProsperity = { newValue -> viewModel.onAction(TeamTabAction.UpdateProsperity(newValue)) },
        updateReputation = { newValue -> viewModel.onAction(TeamTabAction.UpdateReputation(newValue)) },
        openTeamAchievements = { viewModel.onAction(TeamTabAction.OpenTeamAchievements) },
        openGlobalAchievements = { viewModel.onAction(TeamTabAction.OpenGlobalAchievements) },
        playCurrentScenario = { viewModel.onAction(TeamTabAction.RestoreLastScenario) },
        donate = { viewModel.onAction(TeamTabAction.Donate) },
        deleteScenario = { viewModel.onAction(TeamTabAction.DeleteScenario(it)) }
    )
}
package com.rumpilstilstkin.gloomhavenhelper.screens.start.team

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper
import com.rumpilstilstkin.gloomhavenhelper.screens.core.LaunchedScreenEffect

@Composable
fun TeamTabRoute(
    navController: NavHostController,
    viewModel: TeamTabViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val screenEffect by viewModel.screenEvents.collectAsStateWithLifecycle(initialValue = null)

    TeamTabScreen(
        state = uiState,
        selectScenario = { scenario -> viewModel.onAction(TeamTabAction.SelectScenario(scenario)) },
        updateProsperity = { newValue -> viewModel.onAction(TeamTabAction.UpdateProsperity(newValue)) },
        updateReputation = { newValue -> viewModel.onAction(TeamTabAction.UpdateReputation(newValue)) },
        openTeamAchievements = { viewModel.onAction(TeamTabAction.OpenTeamAchievements) },
        openGlobalAchievements = { viewModel.onAction(TeamTabAction.OpenGlobalAchievements) },
        playCurrentScenario = { viewModel.onAction(TeamTabAction.RestoreLastScenario) },
        donate = { viewModel.onAction(TeamTabAction.Donate) },
    )

    LaunchedScreenEffect(
        effect = screenEffect,
        navController = navController,
    )
}

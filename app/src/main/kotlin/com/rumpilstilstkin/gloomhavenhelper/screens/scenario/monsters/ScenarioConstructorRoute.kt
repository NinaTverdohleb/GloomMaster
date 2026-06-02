package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.monsters

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper

@Composable
fun ScenarioConstructorRoute(
    navController: NavHostController,
    viewModel: ScenarioConstructorViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navigationEvents by viewModel.navigationEvents.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(navigationEvents) {
        navigationEvents?.let { event ->
            GlHelperEventHelper.event(
                event = event,
                navController = navController,
            )
        }
    }

    ScenarioConstructorScreen(
        state = uiState,
        back = { viewModel.onAction(ScenarioConstructorAction.Back) },
        toggleMonster = { viewModel.onAction(ScenarioConstructorAction.ToggleMonster(it)) },
        startScenario = { viewModel.onAction(ScenarioConstructorAction.AddMonsters) },
    )
}

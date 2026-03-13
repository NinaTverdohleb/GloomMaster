package com.rumpilstilstkin.gloomhavenhelper.screens.start.scenarios

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper

@Composable
fun ScenariosTabRoute(
    navController: NavHostController,
    viewModel: ScenariosTabViewModel = hiltViewModel(),
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

    ScenariosTabScreen(
        state = uiState,
        completeScenario = { viewModel.onAction(ScenariosTabAction.CompleteScenario(it)) },
        startScenario = { viewModel.onAction(ScenariosTabAction.StartScenario(it)) },
        toggleSection = { viewModel.onAction(ScenariosTabAction.ToggleSection(it)) },
        addScenario = { viewModel.onAction(ScenariosTabAction.AddScenario) },
        deleteScenario = { viewModel.onAction(ScenariosTabAction.DeleteScenario(it)) },
        restoreScenario = { viewModel.onAction(ScenariosTabAction.RestoreScenario(it)) },
    )
}

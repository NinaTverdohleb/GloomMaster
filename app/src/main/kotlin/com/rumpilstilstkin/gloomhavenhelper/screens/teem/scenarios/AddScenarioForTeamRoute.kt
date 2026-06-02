package com.rumpilstilstkin.gloomhavenhelper.screens.teem.scenarios

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper

@Composable
fun AddScenarioForTeamRoute(
    navController: NavHostController,
    viewModel: AddScenarioForTeamViewModel = hiltViewModel(),
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

    AddScenarioForTeamScreen(
        uiState = uiState,
        onSearchTextChange = { viewModel.onAction(AddScenarioForTeamAction.SearchTextChange(it)) },
        onScenarioClick = { viewModel.onAction(AddScenarioForTeamAction.SelectScenario(it)) },
        onDismissDialog = { viewModel.onAction(AddScenarioForTeamAction.DismissDialog) },
        onConfirmAdd = { viewModel.onAction(AddScenarioForTeamAction.ConfirmAddScenario) },
        onBack = { viewModel.onAction(AddScenarioForTeamAction.Back) },
    )
}

package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.screens.core.LaunchedScreenEffect

@Composable
fun AddScenarioForTeamRoute(
    navController: NavHostController,
    viewModel: AddScenarioForTeamViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val screenEffect by viewModel.screenEvents.collectAsStateWithLifecycle(initialValue = null)

    AddScenarioForTeamScreen(
        uiState = uiState,
        onSearchTextChange = { viewModel.onAction(AddScenarioForTeamAction.SearchTextChange(it)) },
        onScenarioClick = { viewModel.onAction(AddScenarioForTeamAction.SelectScenario(it)) },
        onBack = { viewModel.onAction(AddScenarioForTeamAction.Back) },
    )

    LaunchedScreenEffect(
        effect = screenEffect,
        navController = navController,
    )
}

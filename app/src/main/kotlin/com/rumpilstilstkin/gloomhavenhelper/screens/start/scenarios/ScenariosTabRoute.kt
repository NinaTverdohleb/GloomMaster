package com.rumpilstilstkin.gloomhavenhelper.screens.start.scenarios

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.screens.core.LaunchedScreenEffect

@Composable
fun ScenariosTabRoute(
    navController: NavHostController,
    viewModel: ScenariosTabViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ScenariosTabScreen(
        state = uiState,
        toggleSection = { viewModel.onAction(ScenariosTabAction.ToggleSection(it)) },
        addScenario = { viewModel.onAction(ScenariosTabAction.AddScenario) },
        selectScenario = { viewModel.onAction(ScenariosTabAction.SelectScenario(it)) },
    )

    LaunchedScreenEffect(
        effects = viewModel.screenEvents,
        navController = navController,
    )
}

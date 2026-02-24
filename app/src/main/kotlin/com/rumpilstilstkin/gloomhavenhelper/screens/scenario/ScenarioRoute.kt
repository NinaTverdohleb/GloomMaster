package com.rumpilstilstkin.gloomhavenhelper.screens.scenario

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper

@Composable
fun ScenarioRoute(
    navController: NavHostController,
    scenarioId: Int,
    viewModel: ScenarioViewModel = hiltViewModel<ScenarioViewModel, ScenarioViewModel.Factory> { factory ->
        factory.create(scenarioId)
    }
) {
    val navigationEvents by viewModel.navigationEvents.collectAsStateWithLifecycle(initialValue = null)

    ScenarioScreen(
        state = ScenarioUIState(
            name = "Гиблая лужa",
        )
    )

    LaunchedEffect(navigationEvents) {
        navigationEvents?.let { event ->
            GlHelperEventHelper.event(
                event = event,
                navController = navController
            )
        }
    }
}
package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.teams.change

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper

@Composable
fun TeamListDialogRoute(
    navController: NavHostController,
    viewModel: TeamListDialogViewModel = hiltViewModel(),
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

    TeamListDialog(
        state = uiState,
        onDismiss = { viewModel.onAction(TeamListDialogAction.Back) },
        selectTeam = { viewModel.onAction(TeamListDialogAction.SelectTeam(it)) },
    )
}

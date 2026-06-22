package com.rumpilstilstkin.gloomhavenhelper.screens.teem.delete

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper

@Composable
fun DeleteTeamDialogRoute(
    teamId: Int,
    teamName: String,
    navController: NavHostController,
) {
    val viewModel =
        hiltViewModel<DeleteTeamDialogViewModel, DeleteTeamDialogViewModel.Factory> { factory ->
            factory.create(teamId, teamName)
        }
    val navigationEvents by viewModel.navigationEvents.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(navigationEvents) {
        navigationEvents?.let { event ->
            GlHelperEventHelper.event(
                event = event,
                navController = navController,
            )
        }
    }

    DeleteTeamDialogScreen(
        teamName = viewModel.uiState,
        back = { viewModel.onAction(DeleteTeamDialogAction.Back) },
        deleteTeam = { viewModel.onAction(DeleteTeamDialogAction.DeleteTeam) }
    )
}
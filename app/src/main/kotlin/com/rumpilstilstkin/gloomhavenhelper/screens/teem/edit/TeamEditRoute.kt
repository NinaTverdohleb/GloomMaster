package com.rumpilstilstkin.gloomhavenhelper.screens.teem.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper

@Composable
fun TeamEditRoute(
    navController: NavHostController,
    viewModel: TeamEditViewModel = hiltViewModel(),
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

    TeamEditScreen(
        uiState = uiState,
        onNameChange = { viewModel.onAction(TeamEditAction.ChangeTeamName(it)) },
        onTogglePack = { viewModel.onAction(TeamEditAction.TogglePack(it)) },
        back = { viewModel.onAction(TeamEditAction.Back) },
        showDeleteDialog = { viewModel.onAction(TeamEditAction.ShowDeleteConfirmDialog) },
        dismissDeleteDialog = { viewModel.onAction(TeamEditAction.DismissDeleteConfirmDialog) },
        confirmDelete = { viewModel.onAction(TeamEditAction.ConfirmDelete) },
        showTeamListDialog = { viewModel.onAction(TeamEditAction.ShowTeamListDialog) },
        dismissTeamListDialog = { viewModel.onAction(TeamEditAction.DismissTeamListDialog) },
        selectTeam = { viewModel.onAction(TeamEditAction.SelectTeam(it)) },
        shareTeamData = { viewModel.onAction(TeamEditAction.ShareTeam) }
    )
}
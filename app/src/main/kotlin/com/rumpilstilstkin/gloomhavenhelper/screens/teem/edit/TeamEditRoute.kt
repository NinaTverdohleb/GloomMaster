package com.rumpilstilstkin.gloomhavenhelper.screens.teem.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.screens.core.LaunchedScreenEffect

@Composable
fun TeamEditRoute(
    navController: NavHostController,
    viewModel: TeamEditViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val screenEffect by viewModel.screenEvents.collectAsStateWithLifecycle(initialValue = null)

    TeamEditScreen(
        uiState = uiState,
        onNameChange = { viewModel.onAction(TeamEditAction.ChangeTeamName(it)) },
        onTogglePack = { viewModel.onAction(TeamEditAction.TogglePack(it)) },
        back = { viewModel.onAction(TeamEditAction.Back) },
        onDifficultyChange = { viewModel.onAction(TeamEditAction.ChangeDifficultyLevel(it)) },
    )

    LaunchedScreenEffect(
        effect = screenEffect,
        navController = navController,
    )
}

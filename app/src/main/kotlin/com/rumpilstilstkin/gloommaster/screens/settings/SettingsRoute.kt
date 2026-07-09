package com.rumpilstilstkin.gloommaster.screens.settings

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloommaster.screens.core.LaunchedScreenEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsRoute(
    navController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SettingsScreen(
        state = uiState,
        back = { viewModel.onAction(SettingsAction.Back) },
        share = { viewModel.onAction(SettingsAction.ShareTeam) },
        settings = { viewModel.onAction(SettingsAction.TeamSetting) },
        selectTeam = { viewModel.onAction(SettingsAction.SelectTeam(it)) },
        showAllTeam = { viewModel.onAction(SettingsAction.ShowAllTeam) },
        addTeam = { viewModel.onAction(SettingsAction.AddTeam) },
        changeLanguage = { viewModel.onAction(SettingsAction.ChangeLanguage) },
        deleteCurrentTeam = { viewModel.onAction(SettingsAction.DeleteCurrentTeam) },
    )

    LaunchedScreenEffect(
        effects = viewModel.screenEvents,
        navController = navController,
    )
}

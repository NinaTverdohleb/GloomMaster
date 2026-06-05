package com.rumpilstilstkin.gloomhavenhelper.screens.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper
import kotlinx.collections.immutable.persistentListOf

@Composable
fun SettingsRoute(
    navController: NavHostController,
    viewModel: SettingsViewModel = hiltViewModel(),
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

    SettingsScreen(
        state = uiState,
        back = { viewModel.onAction(SettingsAction.Back) },
        share = { viewModel.onAction(SettingsAction.ShareTeam) },
        settings = { viewModel.onAction(SettingsAction.TeamSetting) },
        selectTeam = { viewModel.onAction(SettingsAction.SelectTeam(it.teamId)) },
        showAllTeam = { viewModel.onAction(SettingsAction.ShowAllTeam) },
        addTeam = { viewModel.onAction(SettingsAction.AddTeam) },
        changeLanguage = { viewModel.onAction(SettingsAction.ChangeLanguage) },
    )
}

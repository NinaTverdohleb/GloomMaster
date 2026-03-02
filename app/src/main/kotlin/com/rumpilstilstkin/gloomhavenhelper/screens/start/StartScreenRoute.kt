package com.rumpilstilstkin.gloomhavenhelper.screens.start

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper
import com.rumpilstilstkin.gloomhavenhelper.screens.start.characters.CharactersTab
import com.rumpilstilstkin.gloomhavenhelper.screens.start.team.TeamTab

@Composable
fun StartScreenRoute(
    navController: NavHostController,
    viewModel: StartScreenViewModel = hiltViewModel()
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

    StartScreen(
        state = uiState,
        addTeam = {
            viewModel.onAction(StartScreenAction.CreateTeam)
        },
        back = {
            navController.popBackStack()
        },
        selectTab = { selectedTab ->
            when (selectedTab) {
                StartScreenTab.TEAM -> TeamTab(navController)
                StartScreenTab.CHARACTERS -> CharactersTab(navController)
            }
        }
    )
}
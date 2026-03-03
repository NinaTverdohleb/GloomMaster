package com.rumpilstilstkin.gloomhavenhelper.screens.start

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper
import com.rumpilstilstkin.gloomhavenhelper.screens.start.characters.CharactersTab
import com.rumpilstilstkin.gloomhavenhelper.screens.start.team.TeamTabRoute

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
    val context = LocalContext.current
    StartScreen(
        state = uiState,
        addTeam = {
            viewModel.onAction(StartScreenAction.CreateTeam)
        },
        back = {
            (context as? Activity)?.finishAffinity()
        },
        selectTab = { selectedTab ->
            when (selectedTab) {
                StartScreenTab.TEAM -> TeamTabRoute(navController)
                StartScreenTab.CHARACTERS -> CharactersTab(navController)
                StartScreenTab.SCENARIOS -> CharactersTab(navController) //TODO
            }
        }
    )
}
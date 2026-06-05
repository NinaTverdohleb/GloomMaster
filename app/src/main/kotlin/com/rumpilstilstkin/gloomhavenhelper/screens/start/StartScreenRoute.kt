package com.rumpilstilstkin.gloomhavenhelper.screens.start

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper
import com.rumpilstilstkin.gloomhavenhelper.screens.start.characters.CharactersTabRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.start.scenarios.ScenariosTabRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.start.shop.ShopTabRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.start.team.TeamTabRoute

@Composable
fun StartScreenRoute(
    navController: NavHostController,
    viewModel: StartScreenViewModel = hiltViewModel(),
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
    StartScreen(
        state = uiState,
        settings = { viewModel.onAction(StartScreenAction.Settings) },
        selectTab = { selectedTab ->
            when (selectedTab) {
                StartScreenTab.TEAM -> TeamTabRoute(navController)
                StartScreenTab.CHARACTERS -> CharactersTabRoute(navController)
                StartScreenTab.SCENARIOS -> ScenariosTabRoute(navController)
                StartScreenTab.SHOP -> ShopTabRoute(navController)
            }
        },
        addTeam = { viewModel.onAction(StartScreenAction.AddTeam) },
    )
}

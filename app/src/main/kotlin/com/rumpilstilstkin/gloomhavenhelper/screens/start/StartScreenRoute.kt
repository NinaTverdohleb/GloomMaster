package com.rumpilstilstkin.gloomhavenhelper.screens.start

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper
import com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.teams.AddTeamDialog
import com.rumpilstilstkin.gloomhavenhelper.screens.start.characters.CharactersTabRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.start.scenarios.ScenariosTabRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.start.shop.ShopTabRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.start.team.TeamTabRoute

@Composable
fun StartScreenRoute(
    navController: NavHostController,
    viewModel: StartScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navigationEvents by viewModel.navigationEvents.collectAsStateWithLifecycle(initialValue = null)

    var showAddTeamDialog by remember { mutableStateOf(false) }

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
            showAddTeamDialog = true
        },
        selectTab = { selectedTab ->
            when (selectedTab) {
                StartScreenTab.TEAM -> TeamTabRoute(navController)
                StartScreenTab.CHARACTERS -> CharactersTabRoute(navController)
                StartScreenTab.SCENARIOS -> ScenariosTabRoute(navController)
                StartScreenTab.SHOP -> ShopTabRoute(navController)
            }
        },
        editTeam = { viewModel.onAction(StartScreenAction.EditTeam) }
    )
    val openDocumentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri -> uri?.let { viewModel.onAction(StartScreenAction.ImportTeam(it)) } }
    )
    if (showAddTeamDialog) {
        AddTeamDialog(
            onDismiss = { showAddTeamDialog = false },
            openFile = { openDocumentLauncher.launch(arrayOf("application/json")) },
            onAdd = { name ->
                viewModel.onAction(StartScreenAction.CreateTeam(name))
            }
        )
    }
}
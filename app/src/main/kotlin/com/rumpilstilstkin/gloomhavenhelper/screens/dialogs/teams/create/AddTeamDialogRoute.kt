package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.teams.create

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper
import com.rumpilstilstkin.gloomhavenhelper.screens.start.StartScreen
import com.rumpilstilstkin.gloomhavenhelper.screens.start.StartScreenAction
import com.rumpilstilstkin.gloomhavenhelper.screens.start.StartScreenTab
import com.rumpilstilstkin.gloomhavenhelper.screens.start.StartScreenViewModel
import com.rumpilstilstkin.gloomhavenhelper.screens.start.characters.CharactersTabRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.start.scenarios.ScenariosTabRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.start.shop.ShopTabRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.start.team.TeamTabRoute

@Composable
fun AddTeamDialogRoute(
    navController: NavHostController,
    viewModel: AddTeamDialogViewModel = hiltViewModel(),
) {
    val navigationEvents by viewModel.navigationEvents.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(navigationEvents) {
        navigationEvents?.let { event ->
            GlHelperEventHelper.event(
                event = event,
                navController = navController,
            )
        }
    }
    val openDocumentLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument(),
            onResult = { uri -> uri?.let { viewModel.onAction(AddTeamDialogAction.ImportTeam(it)) } },
        )
    AddTeamDialog(
        onDismiss = { viewModel.onAction(AddTeamDialogAction.Back) },
        openFile = { openDocumentLauncher.launch(arrayOf("application/json")) },
        onAdd = { name ->
            viewModel.onAction(AddTeamDialogAction.CreateTeam(name))
        },
    )
}

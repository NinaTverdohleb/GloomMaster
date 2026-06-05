package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.teams.create

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper

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

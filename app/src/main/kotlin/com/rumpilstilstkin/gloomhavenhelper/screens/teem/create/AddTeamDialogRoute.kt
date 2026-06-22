package com.rumpilstilstkin.gloomhavenhelper.screens.teem.create

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloomhavenhelper.screens.core.OverlayContract

object AddTeamDialogContract : OverlayContract<Unit, Boolean> {

    @Composable
    override fun Content(input: Unit, onDismissWithResult: (Boolean?) -> Unit) {
        AddTeamDialogRoute(
            close = onDismissWithResult
        )
    }
}

@Composable
fun AddTeamDialogRoute(
    close: (Boolean?) -> Unit,
    viewModel: AddTeamDialogViewModel = hiltViewModel(),
) {
    val complete by viewModel.complete.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(complete) {
        complete?.let { event ->
            close(event.success)
        }
    }

    val openDocumentLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument(),
            onResult = { uri -> uri?.let { viewModel.onAction(AddTeamDialogState.ImportTeam(it)) } },
        )
    AddTeamDialog(
        openFile = { openDocumentLauncher.launch(arrayOf("application/json")) },
        onAdd = { name ->
            viewModel.onAction(AddTeamDialogState.CreateTeam(name))
        },
    )
}

package com.rumpilstilstkin.gloommaster.screens.teem.delete

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloommaster.screens.core.OverlayContract
import com.rumpilstilstkin.gloommaster.screens.models.ShortTeamInfoUi

object DeleteTeamDialogContract : OverlayContract<ShortTeamInfoUi, Unit> {
    @Composable
    override fun Content(
        input: ShortTeamInfoUi,
        onDismissWithResult: (Unit?) -> Unit,
    ) {
        DeleteTeamDialogRoute(
            team = input,
            close = onDismissWithResult,
        )
    }
}

@Composable
fun DeleteTeamDialogRoute(
    team: ShortTeamInfoUi,
    close: (Unit?) -> Unit,
    viewModel: DeleteTeamDialogViewModel = hiltViewModel(),
) {
    val complete by viewModel.complete.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(complete) {
        complete?.let {
            close(Unit)
        }
    }

    DeleteTeamDialogScreen(
        teamName = team.teamName,
        close = { close(null) },
        deleteTeam = { viewModel.onAction(DeleteTeamDialogAction.DeleteTeam(team.teamId)) },
    )
}

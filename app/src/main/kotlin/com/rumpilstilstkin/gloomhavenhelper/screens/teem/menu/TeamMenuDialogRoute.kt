package com.rumpilstilstkin.gloomhavenhelper.screens.teem.menu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloomhavenhelper.screens.core.BottomSheetContract
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortTeamInfoUi

object TeamMenuDialogContract : BottomSheetContract<ShortTeamInfoUi, TeamMenuResult> {

    @Composable
    override fun Content(input: ShortTeamInfoUi, onDismissWithResult: (TeamMenuResult?) -> Unit) {
        TeamListDialogRoute(
            team = input,
            close = onDismissWithResult
        )
    }
}

@Composable
fun TeamListDialogRoute(
    team: ShortTeamInfoUi,
    close: (result: TeamMenuResult) -> Unit,
    viewModel: TeamMenuDialogViewModel = hiltViewModel(),
) {
    val complete by viewModel.complete.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(complete) {
        complete?.let { event ->
            close(event.result)
        }
    }

    TeamMenuDialog(
        team = team,
        selectTeam = { viewModel.onAction(TeamMenuDialogAction.SelectTeam(team)) },
        deleteTeam = { viewModel.onAction(TeamMenuDialogAction.DeleteTeam(team)) }
    )
}

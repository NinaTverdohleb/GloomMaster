package com.rumpilstilstkin.gloommaster.screens.teem.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloommaster.screens.core.OverlayContract
import com.rumpilstilstkin.gloommaster.screens.models.ShortTeamInfoUi

object TeamListDialogContract : OverlayContract<Unit, ShortTeamInfoUi> {
    @Composable
    override fun Content(
        input: Unit,
        onDismissWithResult: (ShortTeamInfoUi?) -> Unit,
    ) {
        TeamListDialogRoute(
            close = onDismissWithResult,
        )
    }
}

@Composable
fun TeamListDialogRoute(
    close: (id: ShortTeamInfoUi) -> Unit,
    viewModel: TeamListDialogViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val complete by viewModel.complete.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(complete) {
        complete?.let { event ->
            close(event.team)
        }
    }

    TeamListDialog(
        state = uiState,
        selectTeam = { viewModel.onAction(TeamListDialogAction.SelectTeam(it)) },
    )
}

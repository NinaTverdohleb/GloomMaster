package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.teams

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ShortTeamInfo
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun TeamListDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onAddTeam: (() -> Unit)? = null,
    selectTeam: (Int) -> Unit,
    viewModel: TeamListDialogViewModel = hiltViewModel(),
) {
    if (!showDialog) return

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TeamsDialog(
        teams = uiState.teams,
        canAdd = onAddTeam != null,
        onDismiss = onDismiss,
        selectTeam = selectTeam,
        addTeam = { onAddTeam?.invoke() }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamsDialog(
    teams: List<ShortTeamInfo>,
    canAdd: Boolean = false,
    onDismiss: () -> Unit,
    addTeam: () -> Unit,
    selectTeam: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicAlertDialog(
        modifier = modifier,
        content = {
            Card(
                shape = RoundedCornerShape(16.dp),

                ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    teams.forEach { team ->
                        TeamInfoItem(
                            teamName = team.name,
                            modifier = Modifier.clickable {
                                selectTeam(team.teamId)
                                onDismiss()
                            }
                        )
                    }
                    if (canAdd) {
                        Button(
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .width(240.dp),
                            onClick = {
                                addTeam()
                                onDismiss()
                            },
                            content = {
                                Text("Добавить")
                            }
                        )
                    }
                }
            }
        },
        onDismissRequest = { onDismiss() },
    )
}

@Composable
private fun TeamInfoItem(
    teamName: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(8.dp).fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineSmall,
            text = teamName
        )
    }
}

@Preview
@Composable
private fun TeamListDialogPreview() {
    GloomhavenHalperTheme {
        TeamsDialog(
            teams = listOf(
                ShortTeamInfo.fixture(1, "Team 1"),
                ShortTeamInfo.fixture(2, "Team 2"),
                ShortTeamInfo.fixture(3, "Team 3"),
                ShortTeamInfo.fixture(4, "Team 4"),
            ),
            canAdd = true,
            addTeam = {},
            onDismiss = {},
            selectTeam = {},
        )
    }

}
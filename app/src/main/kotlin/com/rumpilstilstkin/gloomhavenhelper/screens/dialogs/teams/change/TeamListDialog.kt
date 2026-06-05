package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.teams.change

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortTeamInfoUi
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamListDialog(
    state: TeamListDialogState,
    onDismiss: () -> Unit,
    selectTeam: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    GloomAlertDialog(
        modifier = modifier,
        content = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 16.dp),
            ) {
                items(
                    items = state.teams,
                    key = { it.teamId },
                ) { team ->
                    TeamInfoItem(
                        teamName = team.teamName,
                        modifier =
                            Modifier.clickable {
                                selectTeam(team.teamId)
                            },
                    )
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
        modifier =
            modifier
                .padding(8.dp)
                .fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineSmall,
            text = teamName,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Preview
@Composable
private fun TeamListDialogPreview() {
    GloomhavenMasterTheme {
        TeamListDialog(
            state =
                TeamListDialogState(
                    persistentListOf(
                        ShortTeamInfoUi.fixture(teamId = 1, name = "Team 1"),
                        ShortTeamInfoUi.fixture(teamId = 2, name = "Team 2"),
                        ShortTeamInfoUi.fixture(teamId = 3, name = "Team 3"),
                        ShortTeamInfoUi.fixture(teamId = 4, name = "Team 4"),
                    ),
                ),
            onDismiss = {},
            selectTeam = {},
        )
    }
}

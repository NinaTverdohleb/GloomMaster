package com.rumpilstilstkin.gloommaster.screens.teem.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.screens.models.ShortTeamInfoUi
import com.rumpilstilstkin.gloommaster.ui.team.TeamItem
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamListDialog(
    state: TeamListDialogState,
    selectTeam: (ShortTeamInfoUi) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(
            items = state.teams,
            key = { it.teamId },
        ) { team ->
            TeamItem(
                team = team,
                onClick = { selectTeam(team) },
            )
        }
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
            selectTeam = {},
        )
    }
}

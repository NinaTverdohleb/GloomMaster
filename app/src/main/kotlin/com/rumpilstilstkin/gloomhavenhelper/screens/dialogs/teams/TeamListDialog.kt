package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.teams

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortTeamInfoUi
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamsDialog(
    teams: ImmutableList<ShortTeamInfoUi>,
    onDismiss: () -> Unit,
    selectTeam: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    GloomAlertDialog(
        modifier = modifier,
        content = {
            teams.forEach { team ->
                TeamInfoItem(
                    teamName = team.teamName,
                    modifier = Modifier.clickable {
                        selectTeam(team.teamId)
                        onDismiss()
                    }
                )
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
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
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
    GloomhavenHalperTheme {
        TeamsDialog(
            teams = persistentListOf(
                ShortTeamInfoUi(1, "Team 1"),
                ShortTeamInfoUi(2, "Team 2"),
                ShortTeamInfoUi(3, "Team 3"),
                ShortTeamInfoUi(4, "Team 4"),
            ),
            onDismiss = {},
            selectTeam = {},
        )
    }
}
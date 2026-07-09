package com.rumpilstilstkin.gloommaster.screens.teem.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.GloomOutlineButton
import com.rumpilstilstkin.gloommaster.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.screens.models.ShortTeamInfoUi
import com.rumpilstilstkin.gloommaster.ui.team.TeamItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamMenuDialog(
    team: ShortTeamInfoUi,
    selectTeam: () -> Unit,
    deleteTeam: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        TeamItem(team = team)

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            GloomOutlineButton(
                text = stringResource(R.string.select_team),
                onClick = selectTeam,
                modifier = Modifier.fillMaxWidth(),
                isError = false,
                icon = AppIcon.Check,
            )
            GloomOutlineButton(
                text = stringResource(R.string.delete_team),
                onClick = deleteTeam,
                modifier = Modifier.fillMaxWidth(),
                isError = true,
                icon = AppIcon.Delete,
            )
        }
    }
}

@Preview
@Composable
private fun TeamMenuDialogPreview() {
    GloomhavenMasterTheme {
        TeamMenuDialog(
            team = ShortTeamInfoUi.fixture(teamId = 4, name = "Team 4"),
            selectTeam = {},
            deleteTeam = {},
        )
    }
}

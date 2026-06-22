package com.rumpilstilstkin.gloomhavenhelper.screens.teem.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortTeamInfoUi
import com.rumpilstilstkin.gloomhavenhelper.ui.components.buttons.GloomOutlineButton
import com.rumpilstilstkin.gloomhavenhelper.ui.components.items.GloomListItem
import com.rumpilstilstkin.gloomhavenhelper.ui.components.items.LeftItemImage
import com.rumpilstilstkin.gloomhavenhelper.ui.team.TeamItem
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme
import kotlinx.collections.immutable.persistentListOf

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
                icon = painterResource(R.drawable.ic_check),
            )
            GloomOutlineButton(
                text = stringResource(R.string.delete_team),
                onClick = deleteTeam,
                modifier = Modifier.fillMaxWidth(),
                isError = true,
                icon = painterResource(R.drawable.ic_delete),
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
            deleteTeam = {}
        )
    }
}

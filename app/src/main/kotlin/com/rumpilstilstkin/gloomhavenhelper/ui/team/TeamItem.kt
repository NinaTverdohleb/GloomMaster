package com.rumpilstilstkin.gloomhavenhelper.ui.team

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortTeamInfoUi
import com.rumpilstilstkin.gloomhavenhelper.ui.components.items.GloomListItem
import com.rumpilstilstkin.gloomhavenhelper.ui.components.items.LeftItemImage
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@Composable
fun TeamItem(
    team: ShortTeamInfoUi,
    onClick: (() -> Unit)? = null,
) =
    GloomListItem(
        title = team.teamName,
        onClick = onClick,
        leftComponent = {
            LeftItemImage(
                icon = painterResource(R.drawable.ic_team)
            )
        }
    )

@Preview
@Composable
private fun TeamItemPreview() {
    GloomhavenMasterTheme {
        TeamItem(
            team = ShortTeamInfoUi.fixture(teamId = 4, name = "Team 4"),
        )
    }
}
package com.rumpilstilstkin.gloommaster.ui.team

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloommaster.designsystem.components.items.GloomListItem
import com.rumpilstilstkin.gloommaster.designsystem.components.items.LeftItemImage
import com.rumpilstilstkin.gloommaster.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.screens.models.ShortTeamInfoUi

@Composable
fun TeamItem(
    team: ShortTeamInfoUi,
    onClick: (() -> Unit)? = null,
) = GloomListItem(
    title = team.teamName,
    onClick = onClick,
    leftComponent = {
        LeftItemImage(
            icon = AppIcon.Team,
        )
    },
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

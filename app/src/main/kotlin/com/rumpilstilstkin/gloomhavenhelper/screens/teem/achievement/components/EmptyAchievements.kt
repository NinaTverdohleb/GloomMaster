package com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.empty.EmptyView
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.EmptyIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun EmptyAchievements() = EmptyView(
    icon = EmptyIcon.Perks,
    title = stringResource(R.string.empty_team_achievements),
    description = stringResource(R.string.empty_team_achievements_description)
)

@Preview
@Composable
private fun EmptyAchievementsPreview() {
    GloomhavenMasterTheme {
        EmptyAchievements()
    }
}

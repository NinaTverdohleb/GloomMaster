package com.rumpilstilstkin.gloommaster.screens.teem.achievement.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.empty.EmptyView
import com.rumpilstilstkin.gloommaster.designsystem.icons.EmptyIcon
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme

@Composable
fun EmptyAchievements() =
    EmptyView(
        icon = EmptyIcon.Perks,
        title = stringResource(R.string.empty_team_achievements),
        description = stringResource(R.string.empty_team_achievements_description),
    )

@Preview
@Composable
private fun EmptyAchievementsPreview() {
    GloomhavenMasterTheme {
        EmptyAchievements()
    }
}

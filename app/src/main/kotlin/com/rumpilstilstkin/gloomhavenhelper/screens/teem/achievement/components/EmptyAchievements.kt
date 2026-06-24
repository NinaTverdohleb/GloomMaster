package com.rumpilstilstkin.gloomhavenhelper.screens.teem.achievement.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.empty.EmptyView
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.EmptyIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

@Composable
fun EmptyAchievements(modifier: Modifier = Modifier) = EmptyView(
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

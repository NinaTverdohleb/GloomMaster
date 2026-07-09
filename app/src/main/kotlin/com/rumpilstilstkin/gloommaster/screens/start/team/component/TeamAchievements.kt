package com.rumpilstilstkin.gloommaster.screens.start.team.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.GloomCard
import com.rumpilstilstkin.gloommaster.designsystem.components.items.GloomListItem
import com.rumpilstilstkin.gloommaster.designsystem.components.items.LeftItemIcon
import com.rumpilstilstkin.gloommaster.designsystem.components.text.GloomHeaderVariant
import com.rumpilstilstkin.gloommaster.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme

@Composable
fun AchievementView(
    title: String,
    achievements: String,
    modifier: Modifier = Modifier,
    clickAchievement: () -> Unit,
) = Column(
    verticalArrangement = Arrangement.spacedBy(12.dp),
) {
    GloomHeaderVariant(text = title)
    GloomCard(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable { clickAchievement() },
    ) {
        val paddings = if (achievements.isEmpty()) 8.dp else 24.dp
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = paddings,
                        horizontal = 16.dp,
                    ),
        ) {
            if (achievements.isEmpty()) {
                GloomListItem(
                    title = stringResource(R.string.add_achievement),
                    leftComponent = {
                        LeftItemIcon(
                            AppIcon.Plus,
                        )
                    },
                )
            } else {
                Text(
                    text = achievements,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}

@Preview
@Composable
private fun GlobalAchievementPreview() {
    GloomhavenMasterTheme {
        AchievementView(
            title = stringResource(R.string.team_achievements),
            modifier = Modifier.fillMaxWidth(),
            achievements = "City Rule: Militaristic",
            clickAchievement = {},
        )
    }
}

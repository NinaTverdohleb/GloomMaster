package com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.GloomListItem
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.LeftItemIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text.GloomHeaderVariant
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AchievementWithName
import com.rumpilstilstkin.gloomhavenhelper.testtags.screens.start.team.component.AchievementTestTags
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun GlobalAchievement(
    globalAchievements: ImmutableList<AchievementWithName>,
    modifier: Modifier = Modifier,
    clickGlobalAchievement: () -> Unit,
) = AchievementView(
    modifier = modifier.testTag(AchievementTestTags.GLOBAL_BLOCK),
    title = stringResource(R.string.global_achievements),
    achievements = globalAchievements,
    clickAchievement = clickGlobalAchievement,
)

@Composable
internal fun TeamAchievement(
    teamAchievements: ImmutableList<AchievementWithName>,
    modifier: Modifier = Modifier,
    clickTeamAchievement: () -> Unit,
) = AchievementView(
    modifier = modifier.testTag(AchievementTestTags.TEAM_BLOCK),
    title = stringResource(R.string.team_achievements),
    achievements = teamAchievements,
    clickAchievement = clickTeamAchievement,
)

@Composable
private fun AchievementView(
    title: String,
    achievements: ImmutableList<AchievementWithName>,
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
                    text =
                        achievements.joinToString(separator = ", ") { achievement ->
                            if (achievement.maxValue > 1) {
                                "${achievement.name} - ${achievement.value}"
                            } else {
                                achievement.name
                            }
                        },
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
        GlobalAchievement(
            modifier = Modifier.fillMaxWidth(),
            globalAchievements =
                persistentListOf(
                    AchievementWithName.fixture("City Rule: Militaristic"),
                    AchievementWithName.fixture("The Voice: Silenced", value = 2),
                ),
            clickGlobalAchievement = {},
        )
    }
}

@Preview
@Composable
private fun TeamAchievementPreview() {
    GloomhavenMasterTheme {
        TeamAchievement(
            modifier = Modifier.fillMaxWidth(),
            teamAchievements =
                persistentListOf(),
            clickTeamAchievement = {},
        )
    }
}

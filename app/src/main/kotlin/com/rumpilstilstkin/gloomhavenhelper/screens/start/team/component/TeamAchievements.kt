package com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AchievementWithName
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun GlobalAchievement(
    globalAchievements: ImmutableList<AchievementWithName>,
    modifier: Modifier = Modifier,
    clickGlobalAchievement: () -> Unit,
) = GloomCard(
    modifier = modifier.clickable { clickGlobalAchievement() },
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(R.string.global_achievements),
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center,
    )
    Spacer(
        modifier = Modifier.height(16.dp),
    )
    globalAchievements.forEach { achievement ->
        val text =
            if (achievement.maxValue > 1) {
                "${achievement.name} - ${achievement.value}"
            } else {
                achievement.name
            }
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
internal fun TeamAchievement(
    teamAchievements: ImmutableList<AchievementWithName>,
    modifier: Modifier = Modifier,
    clickTeamAchievement: () -> Unit,
) = GloomCard(
    modifier = modifier.clickable { clickTeamAchievement() },
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(R.string.team_achievements),
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center,
    )
    Spacer(
        modifier = Modifier.height(16.dp),
    )
    teamAchievements.forEach { achievement ->
        val text =
            if (achievement.maxValue > 1) {
                "${achievement.name} : ${achievement.value}"
            } else {
                achievement.name
            }
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
        )
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
                persistentListOf(
                    AchievementWithName.fixture("City Rule: Militaristic"),
                    AchievementWithName.fixture("The Voice", value = 2, maxValue = 3),
                ),
            clickTeamAchievement = {},
        )
    }
}

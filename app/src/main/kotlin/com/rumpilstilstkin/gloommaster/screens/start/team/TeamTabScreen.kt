package com.rumpilstilstkin.gloommaster.screens.start.team

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.screens.models.ShortScenarioUI
import com.rumpilstilstkin.gloommaster.screens.models.TeamUI
import com.rumpilstilstkin.gloommaster.screens.start.team.component.AchievementView
import com.rumpilstilstkin.gloommaster.screens.start.team.component.ScenarioBlock
import com.rumpilstilstkin.gloommaster.screens.start.team.component.TeamHeader
import com.rumpilstilstkin.gloommaster.screens.start.team.component.TeamProsperity
import com.rumpilstilstkin.gloommaster.screens.start.team.component.TeamReputation
import com.rumpilstilstkin.gloommaster.testtags.screens.start.team.TeamTabScreenTestTags
import com.rumpilstilstkin.gloommaster.testtags.screens.start.team.component.AchievementTestTags

@Composable
internal fun TeamTabScreen(
    state: TeamTabUiState,
    selectScenario: (ShortScenarioUI) -> Unit,
    updateProsperity: (Int) -> Unit,
    updateReputation: (Int) -> Unit,
    openTeamAchievements: () -> Unit,
    openGlobalAchievements: () -> Unit,
    playCurrentScenario: () -> Unit,
    donate: () -> Unit,
) {
    if (state is TeamTabUiState.Data) {
        val team = state.currentTeam
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .testTag(TeamTabScreenTestTags.ROOT_COLUMN),
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            TeamHeader(
                teamName = team.teamName,
                teamLevel = team.teamLevel,
                modifier = Modifier.fillMaxWidth(),
            )
            TeamReputation(
                reputation = team.teamReputation,
                discount = team.shopDiscount,
                updateReputation = updateReputation,
            )
            TeamProsperity(
                prosperity = team.prosperity,
                updateProsperity = updateProsperity,
                churchValue = team.churchValue,
                churchValueForNextProsperity = team.churchValueForNextProsperity,
                donate = donate,
            )
            ScenarioBlock(
                scenarios = team.teamScenario,
                selectScenario = selectScenario,
                canRestore = team.hasActiveScenario,
                playCurrentScenario = playCurrentScenario,
            )
            AchievementView(
                modifier = Modifier.testTag(AchievementTestTags.GLOBAL_BLOCK),
                title = stringResource(R.string.global_achievements),
                achievements = team.globalAchievements,
                clickAchievement = openGlobalAchievements,
            )
            AchievementView(
                modifier = Modifier.testTag(AchievementTestTags.TEAM_BLOCK),
                title = stringResource(R.string.team_achievements),
                achievements = team.teamAchievements,
                clickAchievement = openTeamAchievements,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1C24, heightDp = 1150)
@Composable
private fun TeamTabScreenPreview() {
    GloomhavenMasterTheme {
        TeamTabScreen(
            state =
                TeamTabUiState.Data(
                    currentTeam = TeamUI.fixture(),
                ),
            selectScenario = {},
            updateProsperity = {},
            updateReputation = {},
            openTeamAchievements = {},
            openGlobalAchievements = {},
            playCurrentScenario = {},
            donate = {},
        )
    }
}

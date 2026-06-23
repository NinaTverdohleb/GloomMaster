package com.rumpilstilstkin.gloomhavenhelper.screens.start.team

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.TeamUI
import com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component.GlobalAchievement
import com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component.ScenarioBlock
import com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component.TeamAchievement
import com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component.TeamHeader
import com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component.TeamProsperity
import com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component.TeamReputation

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
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(32.dp)
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
            GlobalAchievement(
                globalAchievements = team.globalAchievements,
                clickGlobalAchievement = openGlobalAchievements,
            )
            TeamAchievement(
                teamAchievements = team.teamAchievements,
                clickTeamAchievement = openTeamAchievements,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1C24, heightDp = 1150,)
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

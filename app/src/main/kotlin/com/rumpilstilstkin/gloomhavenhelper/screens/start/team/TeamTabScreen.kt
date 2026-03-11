package com.rumpilstilstkin.gloomhavenhelper.screens.start.team

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.screens.models.TeamUI
import com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component.GlobalAchievement
import com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component.ScenarioBlock
import com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component.TeamAchievement
import com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component.TeamHeader
import com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component.TeamProsperity
import com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component.TeamReputation
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
internal fun TeamTabScreen(
    state: TeamTabUiState,
    completeScenario: (Int) -> Unit,
    startScenario: (Int?) -> Unit,
    updateProsperity: (Int) -> Unit,
    updateReputation: (Int) -> Unit,
    openTeamAchievements: () -> Unit,
    openGlobalAchievements: () -> Unit,
) {
    if (state is TeamTabUiState.Data) {
        val team = state.currentTeam
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TeamHeader(
                teamName = team.teamName,
                teamLevel = team.teamLevel,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(32.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(32.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                item {
                    TeamReputation(
                        reputation = team.teamReputation,
                        discount = team.shopDiscount,
                        updateReputation = updateReputation,
                    )
                }
                item {
                    TeamProsperity(
                        prosperity = team.prosperity,
                        updateProsperity = updateProsperity
                    )
                }

                item {
                    ScenarioBlock(
                        scenarios = team.teamScenario,
                        completeScenario = completeScenario,
                        startScenario = startScenario,
                    )
                }
                item {
                    GlobalAchievement(
                        globalAchievements = team.globalAchievements,
                        clickGlobalAchievement = openGlobalAchievements
                    )
                }

                item {
                    TeamAchievement(
                        teamAchievements = team.teamAchievements,
                        clickTeamAchievement = openTeamAchievements
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1C24)
@Composable
private fun TeamTabScreenPreview() {
    GloomhavenHalperTheme {
        TeamTabScreen(
            state = TeamTabUiState.Data(
                currentTeam = TeamUI.fixture()
            ),
            completeScenario = {},
            startScenario = {},
            updateProsperity = {},
            updateReputation = {},
            openTeamAchievements = {},
            openGlobalAchievements = {}
        )
    }
}
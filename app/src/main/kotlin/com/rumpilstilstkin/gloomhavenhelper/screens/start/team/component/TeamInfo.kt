package com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.TeamUI
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
internal fun TeamInfo(
    team: TeamUI,
    characterDetails: (Int) -> Unit,
    completeScenario: (Int) -> Unit,
    startScenario: (Int) -> Unit,
    addScenario: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
    ) {
        TitleTeamRow(
            teamName = team.teamName,
            teamLevel = team.teamLevel,
            modifier = Modifier.fillMaxWidth(),
            onLevelClick = {
                {}
            },
            onTeamNameClick = {

            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))

        StatTeamRow(
            reputation = team.teamReputation,
            prosperity = team.prosperity,
            onReputationClick = {
                {}
            },
            onProsperityClick = {
                {}
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider()

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(weight = 1f, fill = false)
        ) {
            AchievementBlock(
                globalAchievements = team.globalAchievements,
                teamAchievements = team.teamAchievements
            )

            Spacer(modifier = Modifier.height(16.dp))

            ScenarioBlock(
                scenarios = team.teamScenario,
                completeScenario =  completeScenario,
                startScenario = startScenario,
                addScenario = addScenario
            )
        }
    }
}

@Preview
@Composable
private fun ContentSample() {
    GloomhavenHalperTheme {
        TeamInfo(
            team = TeamUI(
                teamId = 1,
                teamLevel = 3,
                teamName = "Team 1",
                teamReputation = 1,
                prosperity = 5,
                teamAchievements = "Первые шаги",
                globalAchievements = "Сбежавшая торговка",
                teamScenario = listOf(
                    ShortScenarioUI(
                        scenarioNumber = 1,
                        scenarioName = "Scenario 1",
                        scenarioRequirements = "Requirements 1"
                    )
                ),
                characters = listOf(
                    CharacterUI(
                        name = "Character 1",
                        level = 1,
                        characterClass = CharacterClassUI(
                            classType = CharacterClassType.Brute,
                            name = "Class 1",
                            imageRes = R.drawable.br,
                        ),
                        teamName = null
                    )
                ),
            ),
            characterDetails = {},
            completeScenario = {},
            startScenario = {},
            addScenario = {}
        )
    }
}
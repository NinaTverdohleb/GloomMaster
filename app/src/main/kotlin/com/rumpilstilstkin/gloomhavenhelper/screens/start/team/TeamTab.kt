package com.rumpilstilstkin.gloomhavenhelper.screens.start.team

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.TeamUI
import com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component.CompanyInfoView
import com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component.EmptyCompanyView
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun TeamTab(
    navController: NavHostController,
    viewModel: TeamTabViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navigationEvents by viewModel.navigationEvents.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(navigationEvents) {
        navigationEvents?.let { event ->
            GlHelperEventHelper.event(
                event = event,
                navController = navController
            )
        }
    }
    Content(
        state = uiState,
        action = viewModel::onAction
    )
}

@Composable
fun Content(
    state: TeamTabUiState,
    action: (TeamTabAction) -> Unit,
) {
    when (state) {
        is TeamTabUiState.Data -> {
            CompanyInfoView(
                modifier = Modifier.padding(16.dp),
                team = state.currentTeam,
                characterDetails = {
                    action.invoke(TeamTabAction.CharacterDetails(it))
                }
            )
        }

        TeamTabUiState.Empty -> EmptyCompanyView {
            action(TeamTabAction.AddTeam)
        }
    }
}

@Preview
@Composable
private fun ContentPreview() {
    GloomhavenHalperTheme {
        Content(
            state = TeamTabUiState.Data(
                currentTeam = TeamUI(
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
            ),
            action = {}
        )
    }
}
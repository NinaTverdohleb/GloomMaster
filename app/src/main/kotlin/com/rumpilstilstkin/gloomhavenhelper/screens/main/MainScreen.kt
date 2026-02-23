package com.rumpilstilstkin.gloomhavenhelper.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.navigation.GlHelperScreens
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.TeamUI
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun MainScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectState by viewModel.effects.collectAsStateWithLifecycle()

    MainDialogs(
        reputation = effectState.reputation,
        prosperity = effectState.prosperity,
        effectState = effectState
    ) {
        viewModel.action(it)
    }

    when (uiState) {
        is MainScreenUiState.Empty -> MainEmptyScreen(modifier = modifier) {
            navController.navigate(GlHelperScreens.TeamCreate)
        }

        is MainScreenUiState.Team -> {
            val state = uiState as MainScreenUiState.Team
            Team(
                team = state.team,
                modifier = modifier,
                onAction = { viewModel.action(it) }
            )
        }
    }
}

@Composable
fun Team(
    team: TeamUI,
    modifier: Modifier = Modifier,
    onAction: (MainScreenAction) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {


    }
}

@Preview
@Composable
private fun TeamPreview() {
    GloomhavenHalperTheme {
        Team(
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
            onAction = {}
        )
    }
}

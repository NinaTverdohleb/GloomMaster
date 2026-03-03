package com.rumpilstilstkin.gloomhavenhelper.screens.start

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.TeamUI
import com.rumpilstilstkin.gloomhavenhelper.screens.start.team.TeamTabScreen
import com.rumpilstilstkin.gloomhavenhelper.screens.start.team.TeamTabUiState
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomBottomNavigationBar
import com.rumpilstilstkin.gloomhavenhelper.ui.components.NavItem
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomToolbarStatus
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomToolbarTitle
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
internal fun StartScreen(
    state: StartScreenState,
    selectTab: @Composable (StartScreenTab) -> Unit,
    addTeam: () -> Unit,
    back: () -> Unit
) {
    var selectedTab by rememberSaveable { mutableStateOf(StartScreenTab.TEAM) }

    Scaffold(
        bottomBar = {
            GloomBottomNavigationBar(
                items = StartScreenTab.entries,
                selectedItem = selectedTab,
                selectTab = { tab ->
                    selectedTab = tab as StartScreenTab
                }
            )
        },
        topBar = {
            if (state is StartScreenState.Team) {
                GloomToolbarStatus(
                    status = state.name,
                    back = back,
                    actions = {
                        IconButton(onClick = addTeam) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                )
            } else {
                GloomToolbarTitle(
                    title = stringResource(R.string.app_name),
                    back = back,
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(vertical = 32.dp, horizontal = 16.dp)
        ) {
            when (state) {
                StartScreenState.Empty -> EmptyTeamScreen(
                    addTeam = addTeam
                )

                is StartScreenState.Team -> selectTab(selectedTab)
            }
        }
    }
}

internal enum class StartScreenTab(
    override val title: String,
    override val iconRes: Int
) : NavItem {
    TEAM("Команда", R.drawable.ic_company),
    CHARACTERS("Персонажи", R.drawable.ic_characters),
    SCENARIOS("Сценарии", R.drawable.ic_scenario),
}

@Preview
@Composable
private fun StartScreenEmptyPreview() {
    GloomhavenHalperTheme {
        StartScreen(
            state = StartScreenState.Empty,
            addTeam = {},
            selectTab = {},
            back = {},
        )
    }
}

@Preview
@Composable
private fun StartScreenPreview() {
    GloomhavenHalperTheme {
        StartScreen(
            state = StartScreenState.Team(
                id = 1,
                name = "Superteam"
            ),
            addTeam = {},
            selectTab = {
                TeamTabScreen(
                    state = TeamTabUiState.Data.fixture(),
                    completeScenario = {},
                    startScenario = {},
                    updateProsperity = {},
                    updateReputation = {},
                )
            },
            back = {},
        )
    }
}

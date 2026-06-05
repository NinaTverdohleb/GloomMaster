package com.rumpilstilstkin.gloomhavenhelper.screens.start

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.screens.start.characters.CharactersTabScreen
import com.rumpilstilstkin.gloomhavenhelper.screens.start.characters.CharactersTabStateUi
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomBottomNavigationBar
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomToolbarTitle
import com.rumpilstilstkin.gloomhavenhelper.ui.components.NavItem
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@Composable
internal fun StartScreen(
    state: StartScreenState,
    selectTab: @Composable (StartScreenTab) -> Unit,
    settings: () -> Unit,
    addTeam: () -> Unit,
) {
    var selectedTab by rememberSaveable { mutableStateOf(StartScreenTab.TEAM) }

    Scaffold(
        bottomBar = {
            GloomBottomNavigationBar(
                items = StartScreenTab.entries,
                selectedItem = selectedTab,
                selectTab = { tab ->
                    selectedTab = tab as StartScreenTab
                },
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth(),
            ) {
                IconButton(
                    onClick = settings,
                    modifier = Modifier.align(Alignment.CenterEnd),
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            when (state) {
                StartScreenState.Empty -> {
                    EmptyTeamScreen(
                        addTeam = addTeam,
                    )
                }

                is StartScreenState.Team -> {
                    selectTab(selectedTab)
                }
            }
        }
    }
}

internal enum class StartScreenTab(
    override val titleRes: Int,
    override val iconRes: Int,
) : NavItem {
    TEAM(R.string.tab_team, R.drawable.ic_company),
    CHARACTERS(R.string.tab_characters, R.drawable.ic_characters),
    SHOP(R.string.tab_shop, R.drawable.ic_shop),
    SCENARIOS(R.string.tab_scenarios, R.drawable.ic_scenario),
}

@Preview
@Composable
private fun StartScreenEmptyPreview() {
    GloomhavenMasterTheme {
        StartScreen(
            state = StartScreenState.Empty,
            settings = {},
            selectTab = {},
            addTeam = {},
        )
    }
}

@Preview(device = "spec:width=411dp,height=891dp")
@Composable
private fun StartScreenPreview() {
    GloomhavenMasterTheme {
        StartScreen(
            state =
                StartScreenState.Team(
                    id = 1,
                    name = "Superteam",
                ),
            settings = {},
            addTeam = {},
            selectTab = {
                CharactersTabScreen(
                    state = CharactersTabStateUi.fixture(),
                    switchAlive = {},
                    addCharacter = {},
                    openCharacterDetails = {},
                    toggleClass = {},
                    changeLevel = { _, _ -> },
                )
            },
        )
    }
}

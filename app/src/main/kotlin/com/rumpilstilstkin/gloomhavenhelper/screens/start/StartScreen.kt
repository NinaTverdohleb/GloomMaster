package com.rumpilstilstkin.gloomhavenhelper.screens.start

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.tabs.GloomBottomNavigationBar
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.tabs.NavItem
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.toolbar.GloomToolbarNoBackAction
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.NavigationIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.screens.start.scenarios.ScenariosTabScreen
import com.rumpilstilstkin.gloomhavenhelper.screens.start.scenarios.ScenariosTabStateUi

@Composable
internal fun StartScreen(
    state: StartScreenState,
    selectTab: @Composable (StartScreenTab) -> Unit,
    settings: () -> Unit,
    addTeam: () -> Unit,
) {
    var selectedTab by rememberSaveable { mutableStateOf(StartScreenTab.TEAM) }

    Scaffold(
        topBar = {
            GloomToolbarNoBackAction(
                title = selectedTab.getTitle(),
                titleIcon = selectedTab.icon,
                actionClick = settings
            )
        },
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
    private val titleRes: Int,
    override val icon: NavigationIcon,
) : NavItem {
    TEAM(R.string.tab_team, NavigationIcon.Team),
    CHARACTERS(R.string.tab_characters, NavigationIcon.Character),
    SHOP(R.string.tab_shop, NavigationIcon.Shop),
    SCENARIOS(R.string.tab_scenarios, NavigationIcon.Scenarios);

    @Composable
    override fun getTitle(): String = stringResource(titleRes)
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
                ScenariosTabScreen(
                    state = ScenariosTabStateUi.fixture(),
                    toggleSection = {},
                    addScenario = {},
                    selectScenario = {},
                )
            },
        )
    }
}

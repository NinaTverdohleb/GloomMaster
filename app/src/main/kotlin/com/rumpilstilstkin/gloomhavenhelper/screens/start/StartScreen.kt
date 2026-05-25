package com.rumpilstilstkin.gloomhavenhelper.screens.start

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Language
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
import com.rumpilstilstkin.gloomhavenhelper.screens.start.characters.CharactersTabScreen
import com.rumpilstilstkin.gloomhavenhelper.screens.start.characters.CharactersTabStateUi
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomBottomNavigationBar
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomToolbarStatus
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomToolbarTitle
import com.rumpilstilstkin.gloomhavenhelper.ui.components.NavItem
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@Composable
internal fun StartScreen(
    state: StartScreenState,
    selectTab: @Composable (StartScreenTab) -> Unit,
    addTeam: () -> Unit,
    editTeam: () -> Unit,
    openLanguagePicker: () -> Unit,
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
                    actions = {
                        LanguageAction(onClick = openLanguagePicker)
                        IconButton(onClick = addTeam) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    statusClick = editTeam
                )
            } else {
                GloomToolbarTitle(
                    title = stringResource(R.string.app_name),
                    actions = {
                        LanguageAction(onClick = openLanguagePicker)
                    }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 32.dp, start = 16.dp, end = 16.dp)
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

@Composable
private fun LanguageAction(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            Icons.Default.Language,
            contentDescription = stringResource(R.string.select_language),
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

internal enum class StartScreenTab(
    override val titleRes: Int,
    override val iconRes: Int
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
            addTeam = {},
            selectTab = {},
            editTeam = {},
            openLanguagePicker = {}
        )
    }
}

@Preview(device = "spec:width=411dp,height=891dp")
@Composable
private fun StartScreenPreview() {
    GloomhavenMasterTheme {
        StartScreen(
            state = StartScreenState.Team(
                id = 1,
                name = "Superteam"
            ),
            addTeam = {},
            selectTab = {
                CharactersTabScreen(
                    state = CharactersTabStateUi.fixture(),
                    switchAlive = {},
                    addCharacter = {},
                    openCharacterDetails = {},
                    toggleClass = {},
                    changeLevel = {_, _ ->}
                )
            },
            editTeam = {},
            openLanguagePicker = {}
        )
    }
}

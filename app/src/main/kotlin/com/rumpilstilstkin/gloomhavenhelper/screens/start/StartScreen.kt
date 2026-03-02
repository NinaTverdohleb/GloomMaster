package com.rumpilstilstkin.gloomhavenhelper.screens.start

import androidx.compose.foundation.layout.Box
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
import com.rumpilstilstkin.gloomhavenhelper.R
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
    TEAM("Команда", R.drawable.ic_fly),
    CHARACTERS("Персонажи", R.drawable.ic_fly),
}

@Preview
@Composable
private fun StartScreenPreview() {
    GloomhavenHalperTheme {
        StartScreen(
            state = StartScreenState.Empty,
            addTeam = {},
            selectTab = {},
            back = {},
        )
    }
}

package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.tabs.GloomTopNavigationBar
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.tabs.NavItem
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.toolbar.GloomToolbar
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.NavigationIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.CharacterGeneralTab
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.CharacterGeneralTabState
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterUI
import com.rumpilstilstkin.gloomhavenhelper.ui.characters.CharacterHeaderItem

@Composable
internal fun CharacterDetailsScreen(
    state: CharacterDetailsStateUi,
    back: () -> Unit,
    showNameDialog: (CharacterUI) -> Unit,
    hideNameDialog: () -> Unit,
    saveName: (String) -> Unit,
    selectTab: @Composable (CharacterDetailsTab) -> Unit,
) {
    /*if (state.showNameDialog) {
        CharacterEditNameDialog(
            currentName = state.name,
            onDismiss = hideNameDialog,
            onSave = saveName,
        )
    }*/

    Scaffold(
        topBar = {
            GloomToolbar(
                title = stringResource(R.string.character_details_title),
                back = back,
            )
        },
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(paddingValues),
        ) {
            CharacterHeaderItem(
                character = state.character,
                onClick = showNameDialog,
            )

            CharactersTabs(
                selectTab = selectTab,
            )
        }
    }
}

internal enum class CharacterDetailsTab(
    private val titleRes: Int,
    override val icon: NavigationIcon,
) : NavItem {
    GENERAL(R.string.tab_general, NavigationIcon.CharacterGeneral),
    STUFF(R.string.tab_items, NavigationIcon.CharacterGoods),
    SKILLS(R.string.tab_skills, NavigationIcon.CharacterPerks),
    ;

    @Composable
    override fun getTitle(): String = stringResource(titleRes)
}

@Composable
internal fun CharactersTabs(
    modifier: Modifier = Modifier,
    selectTab: @Composable (CharacterDetailsTab) -> Unit,
) {
    var selectedTab by rememberSaveable { mutableStateOf(CharacterDetailsTab.GENERAL) }

    Column(
        modifier =
            modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize(),
    ) {
        GloomTopNavigationBar(
            items = CharacterDetailsTab.entries,
            selectedItem = selectedTab,
            selectTab = { tab -> selectedTab = tab as CharacterDetailsTab },
        )
        selectTab(selectedTab)
    }
}

@Preview
@Composable
private fun CharacterDetailsScreenPreview() {
    GloomhavenMasterTheme {
        CharacterDetailsScreen(
            state =
                CharacterDetailsStateUi(
                    character = CharacterUI.fixture(),
                    teamName = "Team",
                ),
            back = {},
            showNameDialog = {},
            hideNameDialog = {},
            saveName = {},
            selectTab = {
                CharacterGeneralTab(
                    state =
                        CharacterGeneralTabState(
                            experience = 150,
                            goldCount = 10,
                            checkMarkCount = 15,
                            hasTeam = false,
                            teamName = null,
                            nextLevel = 175,
                            notes = "Some notes",
                        ),
                    openNotes = {},
                    onLevelUp = {},
                    onGoldChanged = {},
                    onExperienceChanged = {},
                    onCheckedChange = {},
                    choosePersonalQuest = {},
                    onTaskCheckedChange = {},
                    showQuestDetails = {},
                    onTaskCountChanged = { _, _ -> },
                )
            },
        )
    }
}

package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.components.CharacterHeader
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.CharacterGeneralTabContent
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.CharacterGeneralTabState
import com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.character.CharacterEditLevelDialog
import com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.character.DeleteCharacterDialog
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CharacterClassTypeUI
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomToolbarTitle
import com.rumpilstilstkin.gloomhavenhelper.ui.components.NavItem
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.character.CharacterEditNameDialog

@Composable
internal fun CharacterDetailsScreen(
    state: CharacterDetailsStateUi,
    back: () -> Unit,
    showDeleteDialog: () -> Unit,
    hideDeleteDialog: () -> Unit,
    confirmDelete: () -> Unit,
    showNameDialog: () -> Unit,
    hideNameDialog: () -> Unit,
    saveName: (String) -> Unit,
    showChangeLevelDialog: () -> Unit,
    hideChangeLevelDialog: () -> Unit,
    changeLevel: (Int) -> Unit,
    retire: () -> Unit,
    selectTab: @Composable (CharacterDetailsTab) -> Unit
) {
    if (state.showDeleteDialog) {
        DeleteCharacterDialog(
            onDismiss = hideDeleteDialog,
            delete = confirmDelete,
            retire = retire,
            canRetire = state.isActive
        )
    }

    if (state.showNameDialog) {
        CharacterEditNameDialog(
            currentName = state.name,
            onDismiss = hideNameDialog,
            onSave = saveName
        )
    }

    if(state.showChangeLevelDialog) {
        CharacterEditLevelDialog(
            characterLevel = state.level,
            characterName = state.name,
            characterClass = state.type,
            dismiss = hideChangeLevelDialog,
            changeLevel = changeLevel
        )
    }

    Scaffold(
        topBar = {
            GloomToolbarTitle(
                title = stringResource(R.string.character_details_title),
                back = back,
                actions = {
                    IconButton(onClick = showDeleteDialog) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            CharacterHeader(
                modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                characterClass = state.type,
                name = state.name,
                level = state.level,
                onNameClick = showNameDialog,
                clickLevel = showChangeLevelDialog
            )

            CharactersTabs(
                selectTab = selectTab
            )
        }
    }
}

internal enum class CharacterDetailsTab(
    override val titleRes: Int,
    override val iconRes: Int
) : NavItem {
    GENERAL(R.string.tab_general, R.drawable.ic_company),
    STUFF(R.string.tab_items, R.drawable.ic_shop),
    SKILLS(R.string.tab_skills, R.drawable.ic_scenario),
}

@Composable
internal fun CharactersTabs(
    modifier: Modifier = Modifier,
    selectTab: @Composable (CharacterDetailsTab) -> Unit
) {
    var selectedTab by rememberSaveable { mutableStateOf(CharacterDetailsTab.GENERAL) }

    val tabs = CharacterDetailsTab.entries

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        PrimaryTabRow(
            selectedTabIndex = tabs.indexOf(selectedTab),
            modifier = Modifier.fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.surface,
        ) {
            tabs.forEach { tab ->
                Tab(
                    text = { Text(text = stringResource(tab.titleRes)) },
                    selected = selectedTab == tab,
                    onClick = { selectedTab = tab }
                )
            }
        }
        selectTab(selectedTab)
    }
}

@Preview
@Composable
private fun CharacterDetailsScreenPreview() {
    GloomhavenMasterTheme {
        CharacterDetailsScreen(
            state = CharacterDetailsStateUi(
                name = "Character",
                level = 1,
                type = CharacterClassTypeUI.Brute,
                teamName = "Team"
            ),
            back = {},
            showDeleteDialog = {},
            hideDeleteDialog = {},
            confirmDelete = {},
            showNameDialog = {},
            hideNameDialog = {},
            saveName = {},
            showChangeLevelDialog = {},
            hideChangeLevelDialog = {},
            changeLevel = {},
            retire = {},
            selectTab = {
                CharacterGeneralTabContent(
                    content = CharacterGeneralTabState(
                        experience = 150,
                        goldCount = 10,
                        checkMarkCount = 15,
                        hasTeam = false,
                        teamName = null,
                        nextLevel = 175,
                        notes = "Some notes"
                    ),
                    onAction = {}
                )
            }
        )
    }
}
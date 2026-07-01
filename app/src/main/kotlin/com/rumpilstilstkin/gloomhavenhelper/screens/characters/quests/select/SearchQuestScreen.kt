package com.rumpilstilstkin.gloomhavenhelper.screens.characters.quests.select

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text.GloomOutlinedTextSearchField
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.toolbar.GloomToolbar
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.screens.models.PersonalQuestUI
import com.rumpilstilstkin.gloomhavenhelper.ui.quest.QuestItemFilled
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun SearchQuestScreen(
    state: SearchQuestState,
    back: () -> Unit,
    searchTextChange: (String) -> Unit,
    openQuest: (PersonalQuestUI) -> Unit,
) = Scaffold(
    topBar = {
        GloomToolbar(
            title = stringResource(R.string.personal_quest_title),
            back = back,
        )
    },
) { innerPadding ->
    Column(
        modifier =
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        GloomOutlinedTextSearchField(
            modifier = Modifier.fillMaxWidth(),
            value = state.searchText,
            onValueChange = searchTextChange,
            placeholder = stringResource(R.string.search_hint),
        )
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(
                items = state.quests,
                key = { it.id }
            ) { quest ->
                QuestItemFilled(
                    modifier = Modifier.animateItem(),
                    quest = quest,
                    clickItem = { openQuest(quest) },
                )
            }
        }
    }
}

@Preview
@Composable
private fun SearchQuestScreenPreview() {
    GloomhavenMasterTheme {
        SearchQuestScreen(
            state =
                SearchQuestState(
                    quests =
                        persistentListOf(
                            PersonalQuestUI.fixture(),
                            PersonalQuestUI.fixture(
                                id = "512",
                            ),
                        ),
                ),
            back = {},
            searchTextChange = {},
            openQuest = {},
        )
    }
}

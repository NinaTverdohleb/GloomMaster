package com.rumpilstilstkin.gloomhavenhelper.screens.characters.quests.freeselect

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.quests.components.PersonalQuestItem
import com.rumpilstilstkin.gloomhavenhelper.screens.models.PersonalQuestUI
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun SearchQuestScreen(
    characterId: Int,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val viewModel =
        hiltViewModel<SearchQuestViewModel, SearchQuestViewModel.Factory> { factory ->
            factory.create(characterId)
        }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val navigationEvents by viewModel.navigationEvents.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(navigationEvents) {
        navigationEvents?.let { event ->
            GlHelperEventHelper.event(
                event = event,
                navController = navController,
            )
        }
    }

    SearchQuestView(
        state = uiState,
        modifier = modifier,
        onAction = viewModel::onAction,
    )
}

@Composable
private fun SearchQuestView(
    state: SearchQuestState,
    modifier: Modifier = Modifier,
    onAction: (SearchQuestActions) -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(8.dp),
    ) {
        OutlinedTextField(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
            value = state.searchText,
            onValueChange = { onAction(SearchQuestActions.SearchTextChange(it)) },
            label = { Text(stringResource(R.string.search_hint)) },
            colors =
                OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                    cursorColor = MaterialTheme.colorScheme.primary,
                ),
        )
        Spacer(
            modifier = Modifier.height(16.dp),
        )
        LazyColumn(
            modifier = Modifier.weight(1f),
        ) {
            items(state.quests) { quest ->
                PersonalQuestItem(
                    modifier =
                        Modifier
                            .padding(8.dp)
                            .animateItem(),
                    quest = quest,
                    chooseQuest = { onAction(SearchQuestActions.ChooseQuest(quest.id)) },
                )
            }
        }
    }
}

@Preview
@Composable
private fun SearchQuestPreview() {
    GloomhavenMasterTheme {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
        ) {
            SearchQuestView(
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
                onAction = {},
            )
        }
    }
}

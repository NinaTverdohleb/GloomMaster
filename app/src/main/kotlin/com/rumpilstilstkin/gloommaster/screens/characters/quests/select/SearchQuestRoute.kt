package com.rumpilstilstkin.gloommaster.screens.characters.quests.select

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloommaster.screens.core.LaunchedScreenEffect

@Composable
fun SearchQuestRoute(
    characterId: Int,
    navController: NavHostController,
) {
    val viewModel =
        hiltViewModel<SearchQuestViewModel, SearchQuestViewModel.Factory> { factory ->
            factory.create(characterId)
        }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SearchQuestScreen(
        state = uiState,
        back = { viewModel.onAction(SearchQuestActions.Close) },
        searchTextChange = { viewModel.onAction(SearchQuestActions.SearchTextChange(it)) },
        openQuest = { viewModel.onAction(SearchQuestActions.OpenQuest(it)) },
    )

    LaunchedScreenEffect(
        effects = viewModel.screenEvents,
        navController = navController,
    )
}

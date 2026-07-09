package com.rumpilstilstkin.gloommaster.screens.characters.start

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloommaster.screens.core.LaunchedScreenEffect

@Composable
fun CharacterDetailsRoute(
    characterId: Int,
    navController: NavHostController,
) {
    val viewModel =
        hiltViewModel<CharacterDetailsViewModel, CharacterDetailsViewModel.Factory> { factory ->
            factory.create(characterId)
        }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    if (uiState is CharacterDetailsStateUi.Data) {
        CharacterDetailsScreen(
            state = uiState as CharacterDetailsStateUi.Data,
            back = { viewModel.onAction(CharacterDetailsAction.Back) },
            showNameDialog = { viewModel.onAction(CharacterDetailsAction.OpenNameDialog) },
            navController = navController,
        )
    }

    LaunchedScreenEffect(
        effects = viewModel.screenEvents,
        navController = navController,
    )
}

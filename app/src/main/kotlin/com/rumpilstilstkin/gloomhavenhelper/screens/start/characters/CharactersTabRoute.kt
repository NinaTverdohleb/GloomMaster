package com.rumpilstilstkin.gloomhavenhelper.screens.start.characters

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper
import com.rumpilstilstkin.gloomhavenhelper.screens.core.LaunchedScreenEffect

@Composable
fun CharactersTabRoute(
    navController: NavHostController,
    viewModel: CharactersTabViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val screenEffect by viewModel.screenEvents.collectAsStateWithLifecycle(initialValue = null)

    CharactersTabScreen(
        state = uiState,
        addCharacter = { viewModel.onAction(CharactersTabAction.ShowAddCharacterDialog) },
        openCharacterMenu = { viewModel.onAction(CharactersTabAction.CharacterMenu(it)) },
        switchAlive = { viewModel.onAction(CharactersTabAction.SwitchAlive) },
        toggleClass = { viewModel.onAction(CharactersTabAction.SwitchClassAvailability(it)) },
        changeLevel = { characterId, level -> viewModel.onAction(CharactersTabAction.ChangeLevel(characterId, level)) },
    )

    LaunchedScreenEffect(
        effect = screenEffect,
        navController = navController,
    )
}

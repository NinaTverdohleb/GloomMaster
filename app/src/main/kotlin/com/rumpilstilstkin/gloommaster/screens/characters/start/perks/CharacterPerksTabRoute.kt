package com.rumpilstilstkin.gloommaster.screens.characters.start.perks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloommaster.screens.core.LaunchedScreenEffect

@Composable
fun CharacterPerksTabRoute(
    characterId: Int,
    navController: NavHostController,
) {
    val viewModel =
        hiltViewModel<CharacterPerksTabViewModel, CharacterPerksTabViewModel.Factory> { factory ->
            factory.create(characterId)
        }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CharacterPerkTabScreen(
        uiState = uiState,
        addPerk = {
            viewModel.onAction(CharacterPerksTabActions.AddPerk)
        },
        deletePerk = {
            viewModel.onAction(CharacterPerksTabActions.DeletePerk(it))
        },
    )

    LaunchedScreenEffect(
        effects = viewModel.screenEvents,
        navController = navController,
    )
}

package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.general.CharacterGeneralTabRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.goods.CharacterItemsTabRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.perks.CharacterPerksTabRoute
import com.rumpilstilstkin.gloomhavenhelper.screens.core.LaunchedScreenEffect

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
    val screenEffect by viewModel.screenEvents.collectAsStateWithLifecycle(initialValue = null)

    CharacterDetailsScreen(
        state = uiState,
        back = { viewModel.onAction(CharacterDetailsAction.Back) },
        showNameDialog = { viewModel.onAction(CharacterDetailsAction.OpenNameDialog) },
        navController = navController,
    )

    LaunchedScreenEffect(
        effect = screenEffect,
        navController = navController,
    )
}

package com.rumpilstilstkin.gloomhavenhelper.screens.characters.goods.add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper

@Composable
fun AddGoodsForCharacterScreenRoute(
    characterId: Int,
    navController: NavHostController,
) {
    val viewModel =
        hiltViewModel<AddGoodsForCharacterScreenViewModel, AddGoodsForCharacterScreenViewModel.Factory> { factory ->
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

    AddGoodsScreen(
        uiState = uiState,
        onAction = { viewModel.onAction(it) },
    )
}

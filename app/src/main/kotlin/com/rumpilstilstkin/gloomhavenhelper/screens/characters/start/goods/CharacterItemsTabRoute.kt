package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.goods

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper

@Composable
fun CharacterItemsTabRoute(
    characterId: Int,
    navController: NavHostController,
) {
    val viewModel =
        hiltViewModel<CharacterItemsTabViewModel, CharacterItemsTabViewModel.Factory> { factory ->
            factory.create(characterId)
        }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navigationEvents by viewModel.navigationEvents.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(navigationEvents) {
        navigationEvents?.let { event ->
            GlHelperEventHelper.event(
                event = event,
                navController = navController
            )
        }
    }

    CharacterItemsTabScreen(
        goods = uiState,
        deleteGood = {
            viewModel.onAction(CharacterItemsTabActions.DeleteGood(it))
        },
        addGoods = {
            viewModel.onAction(CharacterItemsTabActions.AddGood)
        },
        sellGood = {
            viewModel.onAction(CharacterItemsTabActions.SellGood(it))
        }
    )

}
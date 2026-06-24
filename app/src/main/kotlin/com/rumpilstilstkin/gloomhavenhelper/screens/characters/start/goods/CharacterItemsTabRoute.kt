package com.rumpilstilstkin.gloomhavenhelper.screens.characters.start.goods

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.screens.core.LaunchedScreenEffect

@Composable
fun CharacterItemsTabRoute(
    characterId: Int,
    navController: NavHostController,
) {
    val viewModel =
        hiltViewModel<CharacterGoodsTabViewModel, CharacterGoodsTabViewModel.Factory> { factory ->
            factory.create(characterId)
        }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val screenEffect by viewModel.screenEvents.collectAsStateWithLifecycle(initialValue = null)

    CharacterItemsTabScreen(
        goods = uiState,
        deleteGood = {
            viewModel.onAction(CharacterItemsTabActions.DeleteGood(it))
        },
        addGoods = {
            viewModel.onAction(CharacterItemsTabActions.AddGood)
        },
        goodDetails = {
            viewModel.onAction(CharacterItemsTabActions.GoodDetails(it))
        },
        sellGood = {
            viewModel.onAction(CharacterItemsTabActions.SellGood(it))
        },
    )

    LaunchedScreenEffect(
        effect = screenEffect,
        navController = navController,
    )
}

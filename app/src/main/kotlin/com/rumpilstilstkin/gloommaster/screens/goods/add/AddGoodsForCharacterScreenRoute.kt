package com.rumpilstilstkin.gloommaster.screens.goods.add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloommaster.screens.core.LaunchedScreenEffect

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

    AddGoodsScreen(
        uiState = uiState,
        selectFilter = { viewModel.onAction(AddGoodsForCharacterScreenActions.SelectFilter(it)) },
        changeSearchText = { viewModel.onAction(AddGoodsForCharacterScreenActions.SearchTextChange(it)) },
        selectGood = { viewModel.onAction(AddGoodsForCharacterScreenActions.SelectGood(it)) },
        unselectGood = { viewModel.onAction(AddGoodsForCharacterScreenActions.UnselectGood(it)) },
        addGoods = { viewModel.onAction(AddGoodsForCharacterScreenActions.AddSelectedGoods) },
        back = { viewModel.onAction(AddGoodsForCharacterScreenActions.Close) },
        openGood = { viewModel.onAction(AddGoodsForCharacterScreenActions.OpenGood(it)) },
        buyGoods = { viewModel.onAction(AddGoodsForCharacterScreenActions.BuySelectedGoods) },
    )

    LaunchedScreenEffect(
        effects = viewModel.screenEvents,
        navController = navController,
    )
}

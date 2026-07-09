package com.rumpilstilstkin.gloommaster.screens.teem.goods

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloommaster.screens.core.LaunchedScreenEffect

@Composable
fun AddGoodsForTeamScreenRoute(
    navController: NavHostController,
    viewModel: AddGoodsForTeamViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AddGoodsForTeamScreen(
        uiState = uiState,
        selectFilter = { viewModel.onAction(AddGoodsForTeamAction.SelectFilter(it)) },
        changeSearchText = { viewModel.onAction(AddGoodsForTeamAction.SearchTextChange(it)) },
        selectGood = { viewModel.onAction(AddGoodsForTeamAction.SelectGood(it)) },
        unselectGood = {
            viewModel.onAction(AddGoodsForTeamAction.UnselectGood(it))
        },
        addGoods = { viewModel.onAction(AddGoodsForTeamAction.AddSelectedGoods) },
        back = { viewModel.onAction(AddGoodsForTeamAction.Back) },
        openGood = {},
    )

    LaunchedScreenEffect(
        effects = viewModel.screenEvents,
        navController = navController,
    )
}

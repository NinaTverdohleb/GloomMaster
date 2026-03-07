package com.rumpilstilstkin.gloomhavenhelper.screens.teem.goods

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper

@Composable
fun AddGoodsForTeamScreenRoute(
    navController: NavHostController,
    viewModel: AddGoodsForTeamViewModel = hiltViewModel(),
) {
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
    )
}
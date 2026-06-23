package com.rumpilstilstkin.gloomhavenhelper.screens.start.shop

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.screens.core.LaunchedScreenEffect

@Composable
fun ShopTabRoute(
    navController: NavHostController,
    viewModel: ShopTabViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val screenEffect by viewModel.screenEvents.collectAsStateWithLifecycle(initialValue = null)

    ShopTabScreen(
        state = uiState,
        deleteItem = { viewModel.onAction(ShopTabAction.RemoveGood(it)) },
        selectFilter = { viewModel.onAction(ShopTabAction.SelectFilter(it)) },
        enterSearchText = { viewModel.onAction(ShopTabAction.SearchTextChange(it)) },
        addItems = { viewModel.onAction(ShopTabAction.AddGood) },
        selectItem = { viewModel.onAction(ShopTabAction.OpenGood(it)) }
    )

    LaunchedScreenEffect(
        effect = screenEffect,
        navController = navController
    )
}

package com.rumpilstilstkin.gloomhavenhelper.screens.start.scenarios

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ScenariosTabRoute(
    viewModel: ScenariosTabViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ScenariosTabScreen(
        state = uiState,
    )
}

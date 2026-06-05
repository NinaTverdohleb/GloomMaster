package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.language

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper

@Composable
fun SelectLanguageDialogRoute(
    navController: NavHostController,
    viewModel: SelectLanguageDialogViewModel = hiltViewModel(),
) {
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

    SelectLanguageDialog(
        state = uiState,
        onDismiss = { viewModel.onAction(SelectLanguageDialogAction.Back) },
        selectLanguage = { viewModel.onAction(SelectLanguageDialogAction.SelectLanguage(it)) },
    )
}

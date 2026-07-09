package com.rumpilstilstkin.gloommaster.screens.settings.language

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloommaster.screens.core.OverlayContract

object SelectLanguageContract : OverlayContract<Unit, Unit> {
    @Composable
    override fun Content(
        input: Unit,
        onDismissWithResult: (Unit?) -> Unit,
    ) {
        SelectLanguageDialogRoute(
            close = onDismissWithResult,
        )
    }
}

@Composable
fun SelectLanguageDialogRoute(
    viewModel: SelectLanguageDialogViewModel = hiltViewModel(),
    close: (Unit) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val complete by viewModel.complete.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(complete) {
        complete?.let { event ->
            close(Unit)
        }
    }

    SelectLanguageList(
        state = uiState,
        selectLanguage = { viewModel.onAction(SelectLanguageDialogAction.SelectLanguage(it)) },
    )
}

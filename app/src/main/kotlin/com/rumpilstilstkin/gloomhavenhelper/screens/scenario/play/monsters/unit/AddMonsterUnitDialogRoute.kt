package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.monsters.unit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloomhavenhelper.screens.core.OverlayContract

object AddMonsterUnitDialogContract :
    OverlayContract<AddMonsterUnitDialogInput, AddMonsterUnitDialogResult> {
    @Composable
    override fun Content(
        input: AddMonsterUnitDialogInput,
        onDismissWithResult: (AddMonsterUnitDialogResult?) -> Unit,
    ) {
        AddMonsterUnitDialogRoute(
            input = input,
            close = onDismissWithResult,
        )
    }
}

@Composable
fun AddMonsterUnitDialogRoute(
    input: AddMonsterUnitDialogInput,
    close: (AddMonsterUnitDialogResult?) -> Unit,
    viewModel: AddMonsterUnitDialogViewModel =
        hiltViewModel<AddMonsterUnitDialogViewModel, AddMonsterUnitDialogViewModel.Factory> { factory ->
            factory.create(input)
        },
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val complete by viewModel.complete.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(complete) {
        complete?.let { close(it) }
    }

    AddMonsterUnitDialog(
        monsterName = state.monsterName,
        availableIds = state.availableIds,
        selectedTier = state.selectedTier,
        selectedIds = state.selectedIds,
        selectTier = { viewModel.onAction(AddMonsterUnitDialogAction.SelectTier(it)) },
        toggleId = { viewModel.onAction(AddMonsterUnitDialogAction.ToggleId(it)) },
        spawn = { viewModel.onAction(AddMonsterUnitDialogAction.Spawn) },
    )
}

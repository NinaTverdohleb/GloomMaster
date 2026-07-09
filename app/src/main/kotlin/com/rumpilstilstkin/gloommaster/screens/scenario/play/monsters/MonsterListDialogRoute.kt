package com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloommaster.screens.core.OverlayContract

object MonsterListDialogContract : OverlayContract<MonsterListDialogInput, MonsterListDialogResult> {
    @Composable
    override fun Content(
        input: MonsterListDialogInput,
        onDismissWithResult: (MonsterListDialogResult?) -> Unit,
    ) {
        MonsterListDialogRoute(
            input = input,
            close = onDismissWithResult,
        )
    }
}

@Composable
fun MonsterListDialogRoute(
    input: MonsterListDialogInput,
    close: (MonsterListDialogResult?) -> Unit,
    viewModel: MonsterListDialogViewModel =
        hiltViewModel<MonsterListDialogViewModel, MonsterListDialogViewModel.Factory> { factory ->
            factory.create(input)
        },
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val complete by viewModel.complete.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(complete) {
        complete?.let { close(it) }
    }

    MonsterListDialog(
        monsters = state.monsters,
        selectedIds = state.selectedIds,
        toggleMonster = { viewModel.onAction(MonsterListDialogAction.ToggleMonster(it)) },
        selectMonsters = { viewModel.onAction(MonsterListDialogAction.SelectMonsters) },
    )
}

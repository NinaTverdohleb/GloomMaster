package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.monsters.add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloomhavenhelper.screens.core.OverlayContract

object AddScenarioMonstersDialogContract : OverlayContract<Unit, List<String>> {
    @Composable
    override fun Content(
        input: Unit,
        onDismissWithResult: (List<String>?) -> Unit,
    ) {
        AddScenarioMonstersDialogRoute(
            close = onDismissWithResult,
        )
    }
}

@Composable
fun AddScenarioMonstersDialogRoute(
    close: (List<String>?) -> Unit,
    viewModel: AddScenarioMonstersDialogViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val complete by viewModel.complete.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(complete) {
        complete?.let { close(it.slugs) }
    }

    AddScenarioMonstersDialog(
        monsters = state.monsters,
        selectedSlugs = state.selectedSlugs,
        searchText = state.searchText,
        changeSearchText = { viewModel.onAction(AddScenarioMonstersDialogAction.ChangeSearchText(it)) },
        toggleMonster = { viewModel.onAction(AddScenarioMonstersDialogAction.ToggleMonster(it)) },
        addMonsters = { viewModel.onAction(AddScenarioMonstersDialogAction.AddMonsters) },
    )
}

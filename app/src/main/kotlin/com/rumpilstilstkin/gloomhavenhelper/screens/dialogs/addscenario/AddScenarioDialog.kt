package com.rumpilstilstkin.gloomhavenhelper.screens.dialogs.addscenario

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloomhavenhelper.ui.scenario.ScenarioList
import com.rumpilstilstkin.gloomhavenhelper.ui.scenario.ScenarioListWithDialogs

@Composable
fun AddScenarioDialog(
    addScenarioViewModel: AddScenarioViewModel = hiltViewModel(),
    onDismiss: () -> Unit
) {
    val uiState by addScenarioViewModel.uiState.collectAsStateWithLifecycle()

    AlertDialog(
        title = {
            Text(text = "Доступные сценарии")
        },
        text = {
            ScenarioList(scenarios = uiState.scenarios) {

            }
        },
        onDismissRequest = { onDismiss() },
        confirmButton = { /*TODO*/ }
    )

}
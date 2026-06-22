package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.dialog.delete

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI
import com.rumpilstilstkin.gloomhavenhelper.ui.components.dialogs.ConfirmationDeleteDialog

@Composable
fun DeleteScenarioDialogScreen(
    scenario: ShortScenarioUI,
    deleteScenario: () -> Unit,
    close: () -> Unit
) {
    ConfirmationDeleteDialog(
        title = stringResource(R.string.delete_scenario_dialog_title),
        description = stringResource(
            R.string.delete_scenario_dialog_description,
            scenario.scenarioName
        ),
        onDeleteConfirm = deleteScenario,
        onClose = close
    )
}
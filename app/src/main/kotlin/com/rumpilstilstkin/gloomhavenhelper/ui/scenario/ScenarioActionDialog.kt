package com.rumpilstilstkin.gloomhavenhelper.ui.scenario

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@Composable
fun ScenarioActionDialog(
    scenarioNumber: Int,
    scenarioName: String,
    scenarioRequirements: String,
    location: String,
    confirmText: String = stringResource(R.string.play_scenario),
    onDismiss: () -> Unit,
    completeScenario: () -> Unit,
    startScenario: () -> Unit,
    deleteScenario: (Int) -> Unit,
) {
    GloomAlertDialog(
        onDismissRequest = onDismiss,
        onConfirmRequest = startScenario,
        onNeutralRequest = completeScenario,
        neutralText = stringResource(R.string.complete),
        onNegativeRequest = { deleteScenario(scenarioNumber) },
        confirmText = confirmText,
        content = {
            ScenarioInfoItem(
                scenarioNumber = scenarioNumber,
                scenarioName = scenarioName,
                location = location,
            )
            if (scenarioRequirements.isNotBlank()) {
                GloomCard {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.requirements),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp),
                    )
                    Text(
                        text = scenarioRequirements.split(",").joinToString("\n") { it.trim() },
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        },
    )
}

@Composable
fun ScenarioInfoDialog(
    completed: Boolean,
    scenarioNumber: Int,
    scenarioName: String,
    scenarioRequirements: String,
    location: String,
    restoreScenario: (Int) -> Unit,
    deleteScenario: (Int) -> Unit,
    onDismiss: () -> Unit,
) {
    GloomAlertDialog(
        onDismissRequest = onDismiss,
        onConfirmRequest =
            if (completed) {
                { restoreScenario(scenarioNumber) }
            } else {
                null
            },
        confirmText = stringResource(R.string.restore),
        onNeutralRequest = null,
        onNegativeRequest = { deleteScenario(scenarioNumber) },
        content = {
            ScenarioInfoItem(
                scenarioNumber = scenarioNumber,
                scenarioName = scenarioName,
                location = location,
            )
            if (scenarioRequirements.isNotBlank()) {
                GloomCard {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.requirements),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp),
                    )
                    Text(
                        text = scenarioRequirements.split(",").joinToString("\n") { it.trim() },
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        },
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScenarioInfoDialog2(
    completed: Boolean,
    scenarioNumber: Int,
    scenarioName: String,
    scenarioRequirements: String,
    location: String,
    restoreScenario: (Int) -> Unit,
    deleteScenario: (Int) -> Unit,
    onDismiss: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        ScenarioInfoItem(
            scenarioNumber = scenarioNumber,
            scenarioName = scenarioName,
            location = location,
        )
        if (scenarioRequirements.isNotBlank()) {
            GloomCard {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.requirements),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                )
                Spacer(
                    modifier = Modifier.height(16.dp),
                )
                Text(
                    text = scenarioRequirements.split(",").joinToString("\n") { it.trim() },
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun ScenarioInfoDialogPreview() {
    GloomhavenMasterTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            ScenarioInfoDialog2(
                completed = true,
                scenarioNumber = 1,
                scenarioRequirements = "Requirements",
                scenarioName = "Scenario 1",
                location = "",
                onDismiss = {},
                restoreScenario = {},
                deleteScenario = {},
            )
        }
    }
}


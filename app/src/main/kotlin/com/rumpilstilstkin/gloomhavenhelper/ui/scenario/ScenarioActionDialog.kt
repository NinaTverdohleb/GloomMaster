package com.rumpilstilstkin.gloomhavenhelper.ui.scenario

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@Composable
fun ScenarioActionDialog(
    scenarioNumber: Int,
    scenarioName: String,
    scenarioRequirements: String,
    location: String,
    confirmText: String = "Играть",
    onDismiss: () -> Unit,
    completeScenario: () -> Unit,
    startScenario: () -> Unit,
    deleteScenario: (Int) -> Unit
) {
    GloomAlertDialog(
        onDismissRequest = onDismiss,
        onConfirmRequest = startScenario,
        onNeutralRequest = completeScenario,
        neutralText = "Завершить",
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
                        text = "Требования: ",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    Text(
                        text = scenarioRequirements.split(",").joinToString("\n") { it.trim() },
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
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
        onConfirmRequest = if (completed) {
            { restoreScenario(scenarioNumber) }
        } else null,
        confirmText = "Восстановить",
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
                        text = "Требования: ",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    Text(
                        text = scenarioRequirements.split(",").joinToString("\n") { it.trim() },
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun ScenarioActionDialogPreview() {
    GloomhavenMasterTheme {
        ScenarioActionDialog(
            scenarioNumber = 1,
            scenarioRequirements = "Requirements",
            scenarioName = "Scenario 1",
            location = "",
            onDismiss = {},
            completeScenario = {},
            startScenario = {},
            deleteScenario = {}
        )
    }
}

@Preview
@Composable
private fun ScenarioInfoDialogPreview() {
    GloomhavenMasterTheme {
        ScenarioInfoDialog(
            completed = true,
            scenarioNumber = 1,
            scenarioRequirements = "Requirements",
            scenarioName = "Scenario 1",
            location = "",
            onDismiss = {},
            restoreScenario = {},
            deleteScenario = {}
        )
    }
}
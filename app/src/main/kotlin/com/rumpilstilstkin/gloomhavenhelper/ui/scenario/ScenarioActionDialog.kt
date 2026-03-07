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
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun ScenarioActionDialog(
    scenarioNumber: Int,
    scenarioName: String,
    scenarioRequirements: String,
    location: String,
    onDismiss: () -> Unit,
    completeScenario: () -> Unit,
    startScenario: () -> Unit
) {
    GloomAlertDialog(
        onDismissRequest = onDismiss,
        onConfirmRequest = startScenario,
        onNeutralRequest = null,
        onNegativeRequest = completeScenario,
        confirmText = "Играть",
        negativeText = "Завершить",
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
    scenarioNumber: Int,
    scenarioName: String,
    scenarioRequirements: String,
    location: String,
    onDismiss: () -> Unit,
) {
    GloomAlertDialog(
        onDismissRequest = onDismiss,
        onConfirmRequest = null,
        onNeutralRequest = onDismiss,
        onNegativeRequest = null,
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
    GloomhavenHalperTheme {
        ScenarioActionDialog(
            scenarioNumber = 1,
            scenarioRequirements = "Requirements",
            scenarioName = "Scenario 1",
            location = "",
            onDismiss = {},
            completeScenario = {},
            startScenario = {}
        )
    }
}

@Preview
@Composable
private fun ScenarioInfoDialogPreview() {
    GloomhavenHalperTheme {
        ScenarioInfoDialog(
            scenarioNumber = 1,
            scenarioRequirements = "Requirements",
            scenarioName = "Scenario 1",
            location = "",
            onDismiss = {},
        )
    }
}
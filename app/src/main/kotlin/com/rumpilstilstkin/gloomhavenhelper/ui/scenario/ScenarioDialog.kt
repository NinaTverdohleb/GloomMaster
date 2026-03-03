package com.rumpilstilstkin.gloomhavenhelper.ui.scenario

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun ScenarioDialog(
    scenarioNumber: Int,
    scenarioName: String,
    scenarioRequirements: String,
    location: String,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    completeScenario: () -> Unit,
    startScenario: () -> Unit
) {
    if (showDialog) {
        GloomAlertDialog(
            onDismissRequest = onDismiss,
            onConfirmRequest = startScenario,
            confirmText = "Играть",
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
                OutlinedButton(
                    onClick = completeScenario,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.error),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(0xFF9CA3AF),
                    ),
                ) {
                    Text(
                        text = "Завершить сценарий",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun Sample() {
    GloomhavenHalperTheme {
        ScenarioDialog(
            scenarioNumber = 1,
            scenarioRequirements = "Requirements",
            showDialog = true,
            scenarioName = "Scenario 1",
            location = "",
            onDismiss = {},
            completeScenario = {},
            startScenario = {}
        )
    }
}
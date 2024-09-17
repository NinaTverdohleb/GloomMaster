package com.rumpilstilstkin.gloomhavenhelper.ui.scenario

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun ScenarioDialog(
    scenarioNumber: Int,
    scenarioName: String,
    scenarioRequirements: String,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onComplete: () -> Unit,
    onStart: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss.invoke() },
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier,
                        text = "$scenarioNumber: $scenarioName",
                        textAlign = TextAlign.Center
                    )
                }
            },
            text = {
                Column {
                    Text(
                        style = MaterialTheme.typography.headlineMedium,
                        text = "Требования",
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = scenarioRequirements
                    )
                }

            },
            confirmButton = {
                Column {
                    ElevatedButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onComplete.invoke()
                        }
                    ) {
                        Text("Завершить")
                    }
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            onStart.invoke()
                        }
                    ) {
                        Text("Играть!")
                    }
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
            onDismiss = {},
            onComplete = {},
            onStart = {}
        )
    }
}
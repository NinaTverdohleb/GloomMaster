package com.rumpilstilstkin.gloomhavenhelper.ui.scenario

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomCard

@Composable
fun ScenarioItemWithDialog(
    scenarioNumber: Int,
    scenarioName: String,
    scenarioRequirements: String,
    location: String,
    modifier: Modifier = Modifier,
    onComplete: (Int) -> Unit,
    onStart: (Int) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    ScenarioDialog(
        scenarioNumber = scenarioNumber,
        scenarioName = scenarioName,
        scenarioRequirements = scenarioRequirements,
        showDialog = showDialog,
        onDismiss = { showDialog = false },
        completeScenario = {
            onComplete(scenarioNumber)
            showDialog = false
        },
        location = location,
        startScenario = {
            onStart(scenarioNumber)
            showDialog = false
        }
    )

    ScenarioInfoCardItem(
        scenarioNumber = scenarioNumber,
        scenarioName = scenarioName,
        location = location,
        modifier = modifier
    ) { showDialog = true }
}

@Composable
fun ScenarioInfoCardItem(
    scenarioNumber: Int,
    scenarioName: String,
    location: String,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit = {}
) = GloomCard(
    modifier = modifier
) {
    ScenarioInfoItem(
        scenarioNumber = scenarioNumber,
        scenarioName = scenarioName,
        location = location,
        modifier = modifier,
        onClick = onClick
    )
}

@Composable
fun ScenarioInfoItem(
    scenarioNumber: Int,
    scenarioName: String,
    location: String,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(scenarioNumber) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = modifier
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(8.dp),
                )
                .border(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.outline,
                    width = 1.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = modifier
                    .padding(vertical = 8.dp, horizontal = 8.dp),
                style = MaterialTheme.typography.headlineMedium,
                text = "# $scenarioNumber",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = scenarioName,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (location.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Регион: $location",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }

}

@Preview
@Composable
private fun Sample() {
    GloomhavenHalperTheme {
        ScenarioItemWithDialog(
            scenarioNumber = 99,
            scenarioName = "Name",
            scenarioRequirements = "",
            location = "Полная жопа",
            onComplete = {},
            onStart = {}
        )
    }

}
package com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI
import com.rumpilstilstkin.gloomhavenhelper.ui.scenario.ScenarioActionDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.scenario.ScenarioInfoCardItem
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun ScenarioBlock(
    scenarios: List<ShortScenarioUI>,
    modifier: Modifier = Modifier,
    completeScenario: (Int) -> Unit,
    startScenario: (Int?) -> Unit,
) = Column(
    modifier = modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            style = MaterialTheme.typography.labelLarge,
            text = "ДОСТУПНЫЕ СЦЕНАРИИ",
            color = MaterialTheme.colorScheme.primary
        )

        IconButton(onClick = {startScenario(null)}) {
            Icon(
                Icons.Default.Add,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))

    var selectedScenario by remember { mutableStateOf<ShortScenarioUI?>(null) }

    scenarios.forEachIndexed { index, scenario ->
        ScenarioInfoCardItem(
            scenarioNumber = scenario.scenarioNumber,
            scenarioName = scenario.scenarioName,
            location = scenario.location,
        ) { selectedScenario = scenario }
        if (index != scenarios.lastIndex) {
            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }
    }
    selectedScenario?.let { scenario ->
        ScenarioActionDialog(
            scenarioNumber = scenario.scenarioNumber,
            scenarioName = scenario.scenarioName,
            scenarioRequirements = scenario.scenarioRequirements,
            onDismiss = { selectedScenario = null },
            confirmText = "Играть",
            completeScenario = {
                completeScenario(scenario.scenarioNumber)
                selectedScenario = null
            },
            location = scenario.location,
            startScenario = {
                startScenario(scenario.scenarioNumber)
                selectedScenario = null
            }
        )
    }
}

@Preview
@Composable
private fun ScenarioBlockPreview() {
    GloomhavenHalperTheme {
        ScenarioBlock(
            scenarios = listOf(
                ShortScenarioUI.fixture(1)
            ),
            completeScenario = {},
            startScenario = {},
        )
    }
}
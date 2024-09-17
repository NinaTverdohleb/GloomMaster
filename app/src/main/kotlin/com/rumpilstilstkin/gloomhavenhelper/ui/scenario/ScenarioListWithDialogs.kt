package com.rumpilstilstkin.gloomhavenhelper.ui.scenario

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI

@Composable
fun ScenarioListWithDialogs(
    modifier: Modifier = Modifier,
    scenarios: List<ShortScenarioUI>,
    onComplete: (Int) -> Unit,
    onStart: (Int) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        scenarios.forEach { scenario ->
            ScenarioItemWithDialog(
                scenarioNumber = scenario.scenarioNumber,
                scenarioName = scenario.scenarioName,
                scenarioRequirements = scenario.scenarioRequirements,
                onComplete = onComplete,
                onStart = onStart
            )
        }
    }
}

@Composable
fun ScenarioList(
    scenarios: List<ShortScenarioUI>,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        scenarios.forEach { scenario ->
            ScenarioInfoItem(
                scenarioNumber = scenario.scenarioNumber,
                scenarioName = scenario.scenarioName,
                scenarioRequirements = scenario.scenarioRequirements,
                onClick = onClick
            )
        }
    }
}
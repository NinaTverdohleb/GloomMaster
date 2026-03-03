package com.rumpilstilstkin.gloomhavenhelper.ui.scenario

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

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
        scenarios.forEachIndexed { index, scenario ->
            ScenarioItemWithDialog(
                scenarioNumber = scenario.scenarioNumber,
                scenarioName = scenario.scenarioName,
                scenarioRequirements = scenario.scenarioRequirements,
                location = scenario.location,
                onComplete = onComplete,
                onStart = onStart
            )
            if (index != scenarios.lastIndex) {
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
            }
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
        scenarios.forEachIndexed { index, scenario ->
            ScenarioInfoCardItem(
                scenarioNumber = scenario.scenarioNumber,
                scenarioName = scenario.scenarioName,
                location = scenario.location,
                onClick = onClick
            )

            if (index != scenarios.lastIndex) {
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ScenarioListPreview() {
    GloomhavenHalperTheme {
        ScenarioList(
            scenarios = listOf(
                ShortScenarioUI.fixture(1),
                ShortScenarioUI.fixture(2)
            ),
            onClick = {}
        )
    }
}
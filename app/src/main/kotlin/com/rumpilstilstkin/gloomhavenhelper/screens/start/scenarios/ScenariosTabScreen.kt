package com.rumpilstilstkin.gloomhavenhelper.screens.start.scenarios

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI
import com.rumpilstilstkin.gloomhavenhelper.ui.scenario.ScenarioActionDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.scenario.ScenarioInfoCardItem
import com.rumpilstilstkin.gloomhavenhelper.ui.scenario.ScenarioInfoDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@Composable
internal fun ScenariosTabScreen(
    state: ScenariosTabStateUi,
    completeScenario: (Int) -> Unit,
    startScenario: (Int) -> Unit,
    toggleSection: (ScenarioSectionType) -> Unit,
    addScenario: () -> Unit,
    deleteScenario: (Int) -> Unit,
    restoreScenario: (Int) -> Unit,
) = Column(
    modifier = Modifier.fillMaxSize(),
) {
    var selectedActiveScenario by remember { mutableStateOf<ShortScenarioUI?>(null) }
    var selectedInfoScenario by remember { mutableStateOf<ShortScenarioUI?>(null) }

    LazyColumn(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {

        state.sections.entries.forEach {(key, section) ->
            item(key = key.name) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable{
                            toggleSection(key)
                        }
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 16.dp),
                        style = MaterialTheme.typography.headlineSmall,
                        text = key.title.uppercase(),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            if (section.isExpanded) {
                items(
                    items = section.scenarios,
                    key = { it.scenarioNumber }
                ) { scenario ->
                    ScenarioInfoCardItem(
                        modifier = Modifier.animateItem(
                            fadeInSpec = tween(300),
                            fadeOutSpec = tween(300),
                            placementSpec = spring(stiffness = Spring.StiffnessLow)
                        ),
                        scenarioNumber = scenario.scenarioNumber,
                        scenarioName = scenario.scenarioName,
                        location = scenario.location,
                    ) {
                        if (key.isActive) {
                            selectedActiveScenario = scenario
                        } else {
                            selectedInfoScenario = scenario
                        }
                    }
                }
            }
        }
    }

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onClick = addScenario,
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(
            text = "Добавить сценарий",
            fontSize = 16.sp
        )
    }
    selectedActiveScenario?.let { scenario ->
        ScenarioActionDialog(
            scenarioNumber = scenario.scenarioNumber,
            scenarioName = scenario.scenarioName,
            scenarioRequirements = scenario.scenarioRequirements,
            onDismiss = { selectedActiveScenario = null },
            completeScenario = {
                completeScenario(scenario.scenarioNumber)
                selectedActiveScenario = null
            },
            location = scenario.location,
            startScenario = {
                startScenario(scenario.scenarioNumber)
                selectedActiveScenario = null
            },
            deleteScenario = {
                deleteScenario(it)
                selectedActiveScenario = null
            }
        )
    }

    selectedInfoScenario?.let { scenario ->
        ScenarioInfoDialog(
            completed = scenario.complated,
            scenarioNumber = scenario.scenarioNumber,
            scenarioName = scenario.scenarioName,
            scenarioRequirements = scenario.scenarioRequirements,
            onDismiss = { selectedInfoScenario = null },
            location = scenario.location,
            restoreScenario = {
                restoreScenario(it)
                selectedInfoScenario = null
            },
            deleteScenario = {
                deleteScenario(it)
                selectedInfoScenario = null
            },
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1C24)
@Composable
private fun ScenariosTabScreenPreview() {
    GloomhavenMasterTheme {
        ScenariosTabScreen(
            state = ScenariosTabStateUi.fixture(),
            completeScenario = {},
            startScenario = {},
            toggleSection = {},
            addScenario = {},
            deleteScenario = {},
            restoreScenario = {}
        )
    }
}

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
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI
import com.rumpilstilstkin.gloomhavenhelper.ui.components.buttons.GloomFab
import com.rumpilstilstkin.gloomhavenhelper.ui.components.text.GloomHeader
import com.rumpilstilstkin.gloomhavenhelper.ui.scenario.ScenarioInfoCardItem
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

@Composable
internal fun ScenariosTabScreen(
    state: ScenariosTabStateUi,
    toggleSection: (ScenarioSectionType) -> Unit,
    addScenario: () -> Unit,
    selectScenario: (ShortScenarioUI) -> Unit
) = Scaffold(
    floatingActionButtonPosition = FabPosition.End,
    floatingActionButton = {
        GloomFab(
            painter = painterResource(R.drawable.ic_plus),
            onClick = addScenario,
        )
    }
) { innerPadding ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
    ) {

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp),
        ) {
            state.sections.entries.forEach { (key, section) ->
                item(key = key.name) {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    toggleSection(key)
                                }
                                .padding(vertical = 16.dp),
                    ) {
                        GloomHeader(
                            text = stringResource(key.titleRes),
                        )
                    }
                }

                if (section.isExpanded) {
                    items(
                        items = section.scenarios,
                        key = { it.scenarioNumber },
                    ) { scenario ->
                        ScenarioInfoCardItem(
                            modifier =
                                Modifier.animateItem(
                                    fadeInSpec = tween(300),
                                    fadeOutSpec = tween(300),
                                    placementSpec = spring(stiffness = Spring.StiffnessLow),
                                ),
                            scenarioNumber = scenario.scenarioNumber,
                            scenarioName = scenario.scenarioName,
                            location = scenario.location,
                        ) {
                            selectScenario(scenario)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1C24)
@Composable
private fun ScenariosTabScreenPreview() {
    GloomhavenMasterTheme {
        ScenariosTabScreen(
            state = ScenariosTabStateUi.fixture(),
            toggleSection = {},
            addScenario = {},
            selectScenario = {},
        )
    }
}

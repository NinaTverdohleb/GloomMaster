package com.rumpilstilstkin.gloommaster.screens.start.scenarios

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.GloomFab
import com.rumpilstilstkin.gloommaster.designsystem.components.text.GloomHeader
import com.rumpilstilstkin.gloommaster.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.screens.models.ShortScenarioUI
import com.rumpilstilstkin.gloommaster.testtags.screens.start.scenarios.ScenariosTabTestTags
import com.rumpilstilstkin.gloommaster.ui.scenario.ScenarioInfoCardItem

@Composable
internal fun ScenariosTabScreen(
    state: ScenariosTabStateUi,
    toggleSection: (ScenarioSectionType) -> Unit,
    addScenario: () -> Unit,
    selectScenario: (ShortScenarioUI) -> Unit,
) = Scaffold(
    floatingActionButtonPosition = FabPosition.End,
    floatingActionButton = {
        GloomFab(
            icon = AppIcon.Plus,
            onClick = addScenario,
            modifier = Modifier.testTag(ScenariosTabTestTags.ADD_FAB),
        )
    },
) { innerPadding ->
    Column(
        modifier =
            Modifier
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
                                .testTag(ScenariosTabTestTags.section(key.name))
                                .clickable {
                                    toggleSection(key)
                                }.padding(vertical = 16.dp),
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
                                Modifier
                                    .testTag(ScenariosTabTestTags.scenarioCard(scenario.scenarioNumber))
                                    .animateItem(
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

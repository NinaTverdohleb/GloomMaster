package com.rumpilstilstkin.gloomhavenhelper.screens.start.team.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons.GloomButton
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text.GloomHeader
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI
import com.rumpilstilstkin.gloomhavenhelper.testtags.screens.start.team.component.ScenarioBlockTestTags
import com.rumpilstilstkin.gloomhavenhelper.ui.scenario.ScenarioInfoCardItem

@Composable
fun ScenarioBlock(
    scenarios: List<ShortScenarioUI>,
    canRestore: Boolean,
    modifier: Modifier = Modifier,
    selectScenario: (ShortScenarioUI) -> Unit,
    playCurrentScenario: () -> Unit,
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(20.dp),
) {
    GloomHeader(
        stringResource(R.string.available_scenarios),
    )

    if (canRestore) {
        GloomButton(
            text = stringResource(R.string.continue_button),
            modifier = Modifier.fillMaxWidth(),
            onClick = { playCurrentScenario() },
        )
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        scenarios.forEachIndexed { index, scenario ->
            ScenarioInfoCardItem(
                modifier = Modifier.testTag(ScenarioBlockTestTags.scenarioCard(index)),
                scenarioNumber = scenario.scenarioNumber,
                scenarioName = scenario.scenarioName,
                location = scenario.location,
            ) { selectScenario(scenario) }
        }
    }
}

@Preview
@Composable
private fun ScenarioBlockPreview() {
    GloomhavenMasterTheme {
        ScenarioBlock(
            scenarios =
                listOf(
                    ShortScenarioUI.fixture(1),
                ),
            canRestore = true,
            selectScenario = {},
            playCurrentScenario = {},
        )
    }
}

package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.dialog.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons.GloomOutlineButton
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI
import com.rumpilstilstkin.gloomhavenhelper.screens.models.toHumanReadable
import com.rumpilstilstkin.gloomhavenhelper.ui.scenario.ScenarioInfoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScenarioDialog(
    scenario: ShortScenarioUI,
    addScenario: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        ScenarioInfoItem(
            scenarioNumber = scenario.scenarioNumber,
            scenarioName = scenario.scenarioName,
            location = scenario.location,
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = stringResource(R.string.requirements),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Text(
                text = scenario.scenarioRequirements.toHumanReadable(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 8.dp),
            )
        }

        GloomOutlineButton(
            text = stringResource(R.string.add_scenario),
            onClick = addScenario,
            modifier = Modifier.fillMaxWidth(),
            isError = false,
            icon = AppIcon.Check,
        )
    }
}

@Preview
@Composable
private fun AddScenarioDialogPreview() {
    GloomhavenMasterTheme {
        AddScenarioDialog(
            scenario = ShortScenarioUI.fixture(1),
            addScenario = {},
        )
    }
}

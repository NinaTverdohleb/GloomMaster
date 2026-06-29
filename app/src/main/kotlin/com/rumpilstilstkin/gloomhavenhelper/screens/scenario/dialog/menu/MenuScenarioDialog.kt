package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.dialog.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
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
fun MenuScenarioDialog(
    scenario: ShortScenarioUI,
    deleteScenario: () -> Unit,
    restoreScenario: () -> Unit,
    competeScenario: () -> Unit,
    playScenario: () -> Unit,
) {
    Column(
        modifier =
            Modifier
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
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            GloomOutlineButton(
                text = stringResource(R.string.delete_scenario),
                onClick = deleteScenario,
                modifier = Modifier.fillMaxWidth(),
                isError = true,
                icon = AppIcon.Delete,
            )

            if (scenario.completed) {
                GloomOutlineButton(
                    text = stringResource(R.string.restore),
                    onClick = restoreScenario,
                    modifier = Modifier.fillMaxWidth(),
                    isError = false,
                    icon = AppIcon.Restore,
                )
            }
            if (scenario.avaliable) {
                GloomOutlineButton(
                    text = stringResource(R.string.complete_scenario),
                    onClick = competeScenario,
                    modifier = Modifier.fillMaxWidth(),
                    isError = false,
                    icon = AppIcon.Check,
                )

                GloomOutlineButton(
                    text = stringResource(R.string.play_scenario),
                    onClick = playScenario,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .testTag(MenuScenarioDialogTestTags.PLAY_SCENARIO_BUTTON),
                    isError = false,
                    icon = AppIcon.Play,
                )
            }
        }
    }
}

object MenuScenarioDialogTestTags {
    const val PLAY_SCENARIO_BUTTON = "MenuScenarioDialogPlayScenarioButton"
}

@Preview
@Composable
private fun MenuScenarioDialogPreview() {
    GloomhavenMasterTheme {
        MenuScenarioDialog(
            scenario = ShortScenarioUI.fixture(1),
            deleteScenario = {},
            restoreScenario = {},
            competeScenario = {},
            playScenario = {},
        )
    }
}

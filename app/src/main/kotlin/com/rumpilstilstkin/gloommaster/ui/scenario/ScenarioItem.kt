package com.rumpilstilstkin.gloommaster.ui.scenario

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.items.GloomListFilledItem
import com.rumpilstilstkin.gloommaster.designsystem.components.items.GloomListItem
import com.rumpilstilstkin.gloommaster.designsystem.components.items.LeftItemNumber
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme

@Composable
fun ScenarioInfoCardItem(
    scenarioNumber: Int,
    scenarioName: String,
    location: String,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit = {},
) = GloomListFilledItem(
    modifier = modifier,
    title = scenarioName,
    description = location,
    onClick = { onClick(scenarioNumber) },
    leftComponent = {
        LeftItemNumber(
            number = stringResource(R.string.scenario_number_format, scenarioNumber.toString()),
        )
    },
)

@Composable
fun ScenarioInfoItem(
    scenarioNumber: Int,
    scenarioName: String,
    location: String,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit = {},
) {
    GloomListItem(
        modifier = modifier,
        title = scenarioName,
        description = location,
        onClick = { onClick(scenarioNumber) },
        leftComponent = {
            LeftItemNumber(
                number = stringResource(R.string.scenario_number_format, scenarioNumber.toString()),
            )
        },
    )
}

@Preview
@Composable
private fun Sample() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ScenarioInfoCardItem(
                scenarioNumber = 99,
                scenarioName = "Name",
                location = "Bad place",
            )

            ScenarioInfoItem(
                scenarioNumber = 99,
                scenarioName = "Name",
                location = "Bad place",
            )
        }
    }
}

package com.rumpilstilstkin.gloomhavenhelper.ui.scenario

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.GloomListFilledItem
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.GloomListItem
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.items.LeftItemNumber
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme

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
            number = stringResource(R.string.scenario_number_format, scenarioNumber.toString())
        )
    }
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
                number = stringResource(R.string.scenario_number_format, scenarioNumber.toString())
            )
        }
    )
}

@Preview
@Composable
private fun Sample() {
    GloomhavenMasterTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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

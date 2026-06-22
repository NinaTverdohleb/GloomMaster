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
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.ui.components.items.GloomListFilledItem
import com.rumpilstilstkin.gloomhavenhelper.ui.components.items.GloomListItem
import com.rumpilstilstkin.gloomhavenhelper.ui.components.items.LeftItemNumber
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme

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
            number = stringResource(R.string.scenario_number_format, scenarioNumber)
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
                number = stringResource(R.string.scenario_number_format, scenarioNumber)
            )
        }
    )
}


@Composable
fun ScenarioInfoItem2(
    scenarioNumber: Int,
    scenarioName: String,
    location: String,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit = {},
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onClick(scenarioNumber) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                modifier
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(8.dp),
                    )
                    .border(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.outline,
                        width = 1.dp,
                    ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier =
                    modifier
                        .padding(vertical = 8.dp, horizontal = 8.dp),
                style = MaterialTheme.typography.headlineMedium,
                text = stringResource(R.string.scenario_number_format, scenarioNumber),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = scenarioName,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
            if (location.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.location_format, location),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
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

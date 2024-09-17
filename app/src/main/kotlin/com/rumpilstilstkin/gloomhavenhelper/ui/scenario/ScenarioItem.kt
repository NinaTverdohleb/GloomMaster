package com.rumpilstilstkin.gloomhavenhelper.ui.scenario

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioInfo
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun ScenarioItemWithDialog(
    scenarioNumber: Int,
    scenarioName: String,
    scenarioRequirements: String,
    modifier: Modifier = Modifier,
    onComplete: (Int) -> Unit,
    onStart: (Int) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    ScenarioDialog(
        scenarioNumber = scenarioNumber,
        scenarioName = scenarioName,
        scenarioRequirements = scenarioRequirements,
        showDialog = showDialog,
        onDismiss = { showDialog = false },
        onComplete = {
            onComplete(scenarioNumber)
            showDialog = false
        },
        onStart = {
            onStart(scenarioNumber)
            showDialog = false
        }
    )

    ScenarioInfoItem(
        scenarioNumber = scenarioNumber,
        scenarioName = scenarioName,
        scenarioRequirements = scenarioRequirements,
        modifier = modifier
    ){
        showDialog = true
    }
}

@Composable
fun ScenarioInfoItem(
    scenarioNumber: Int,
    scenarioName: String,
    scenarioRequirements: String,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(scenarioNumber) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = modifier
                .size(52.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                style = MaterialTheme.typography.titleSmall,
                text = scenarioNumber.toString(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = scenarioName,
                style = MaterialTheme.typography.bodyLarge,
            )
            if (scenarioRequirements.isNotBlank()) {
                Text(
                    text = scenarioRequirements,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }

}

@Preview
@Composable
private fun Sample() {
    GloomhavenHalperTheme {
        ScenarioItemWithDialog(
            scenarioNumber = 99,
            scenarioName = "Name",
            scenarioRequirements = "",
            onComplete = {},
            onStart = {}
        )
    }

}
package com.rumpilstilstkin.gloomhavenhelper.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI
import com.rumpilstilstkin.gloomhavenhelper.ui.scenario.ScenarioListWithDialogs

@Composable
fun ScenarioBlock(
    scenrios: List<ShortScenarioUI>,
    modifier: Modifier = Modifier,
    onAction: (MainScreenAction) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Сценарии",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))

        ScenarioListWithDialogs(
            scenarios = scenrios,
            onComplete = {
                onAction.invoke(MainScreenAction.CompleteScenario(it))
            },
            onStart = {
                //onAction.invoke(MainScreenAction.StartScenario(it))
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                //onAction.invoke(MainScreenAction.ShowAddCharacterDialog)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Добавить сценарий")
        }
    }

}
package com.rumpilstilstkin.gloomhavenhelper.screens.teem.scenarios

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomAlertDialog
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomToolbarTitle
import com.rumpilstilstkin.gloomhavenhelper.ui.scenario.ScenarioInfoCardItem
import com.rumpilstilstkin.gloomhavenhelper.ui.scenario.ScenarioInfoItem
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun AddScenarioForTeamScreen(
    uiState: AddScenarioForTeamUiState,
    onSearchTextChange: (String) -> Unit,
    onScenarioClick: (ShortScenarioUI) -> Unit,
    onDismissDialog: () -> Unit,
    onConfirmAdd: () -> Unit,
    onBack: () -> Unit,
) = Scaffold(
    topBar = {
        GloomToolbarTitle(
            title = "Добавить сценарий",
            back = onBack,
        )
    },
) { paddingValues ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
    ) {
        OutlinedTextField(
            value = uiState.searchText,
            onValueChange = onSearchTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            placeholder = { Text("Поиск по номеру или названию") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            singleLine = true,
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(
                items = uiState.scenarios,
                key = { it.scenarioNumber }
            ) { scenario ->
                ScenarioInfoCardItem(
                    scenarioNumber = scenario.scenarioNumber,
                    scenarioName = scenario.scenarioName,
                    location = scenario.location,
                    onClick = { onScenarioClick(scenario) }
                )
            }
        }
    }

    uiState.selectedScenario?.let { scenario ->
        AddScenarioConfirmDialog(
            scenario = scenario,
            onDismiss = onDismissDialog,
            onConfirm = onConfirmAdd,
        )
    }
}

@Composable
private fun AddScenarioConfirmDialog(
    scenario: ShortScenarioUI,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    GloomAlertDialog(
        title = "Добавить сценарий?",
        onDismissRequest = onDismiss,
        onConfirmRequest = onConfirm,
        onNeutralRequest = onDismiss,
        confirmText = "Добавить",
        content = {
            ScenarioInfoItem(
                scenarioNumber = scenario.scenarioNumber,
                scenarioName = scenario.scenarioName,
                location = scenario.location,
            )
            if (scenario.scenarioRequirements.isNotBlank()) {
                Text(
                    text = "Требования: ${scenario.scenarioRequirements}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1C24)
@Composable
private fun AddScenarioForTeamScreenPreview() {
    GloomhavenMasterTheme {
        AddScenarioForTeamScreen(
            uiState = AddScenarioForTeamUiState(
                scenarios = persistentListOf(
                    ShortScenarioUI.fixture(1),
                    ShortScenarioUI.fixture(2),
                    ShortScenarioUI.fixture(3),
                ),
                searchText = "",
                selectedScenario = null,
            ),
            onSearchTextChange = {},
            onScenarioClick = {},
            onDismissDialog = {},
            onConfirmAdd = {},
            onBack = {},
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1C24)
@Composable
private fun AddScenarioConfirmDialogPreview() {
    GloomhavenMasterTheme {
        AddScenarioConfirmDialog(
            scenario = ShortScenarioUI.fixture(1),
            onDismiss = {},
            onConfirm = {},
        )
    }
}

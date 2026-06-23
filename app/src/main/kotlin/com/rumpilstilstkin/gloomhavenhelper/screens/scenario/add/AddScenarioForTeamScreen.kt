package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.text.GloomOutlinedTextSearchField
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ShortScenarioUI
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.toolbar.GloomToolbar
import com.rumpilstilstkin.gloomhavenhelper.ui.scenario.ScenarioInfoCardItem
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun AddScenarioForTeamScreen(
    uiState: AddScenarioForTeamUiState,
    onSearchTextChange: (String) -> Unit,
    onScenarioClick: (ShortScenarioUI) -> Unit,
    onBack: () -> Unit,
) = Scaffold(
    topBar = {
        GloomToolbar(
            title = stringResource(R.string.add_scenario_title),
            back = onBack,
        )
    },
) { paddingValues ->
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
    ) {
        GloomOutlinedTextSearchField(
            value = uiState.searchText,
            onValueChange = onSearchTextChange,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp, top = 4.dp),
            placeholder = stringResource(R.string.search_scenarios_placeholder),
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(
                items = uiState.scenarios,
                key = { it.scenarioNumber },
            ) { scenario ->
                ScenarioInfoCardItem(
                    scenarioNumber = scenario.scenarioNumber,
                    scenarioName = scenario.scenarioName,
                    location = scenario.location,
                    onClick = { onScenarioClick(scenario) },
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1C24)
@Composable
private fun AddScenarioForTeamScreenPreview() {
    GloomhavenMasterTheme {
        AddScenarioForTeamScreen(
            uiState =
                AddScenarioForTeamUiState(
                    scenarios =
                        persistentListOf(
                            ShortScenarioUI.fixture(1),
                            ShortScenarioUI.fixture(2),
                            ShortScenarioUI.fixture(3),
                        ),
                    searchText = "",
                ),
            onSearchTextChange = {},
            onScenarioClick = {},
            onBack = {},
        )
    }
}

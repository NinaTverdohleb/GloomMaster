package com.rumpilstilstkin.gloommaster.screens.scenario.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.text.GloomOutlinedTextSearchField
import com.rumpilstilstkin.gloommaster.designsystem.components.toolbar.GloomToolbar
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.screens.models.ShortScenarioUI
import com.rumpilstilstkin.gloommaster.testtags.screens.scenario.add.AddScenarioForTeamScreenTestTags
import com.rumpilstilstkin.gloommaster.ui.scenario.ScenarioInfoCardItem
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
            itemsIndexed(
                items = uiState.scenarios,
                key = { _, scenario -> scenario.scenarioNumber },
            ) { index, scenario ->
                ScenarioInfoCardItem(
                    modifier = Modifier.testTag(AddScenarioForTeamScreenTestTags.scenario(index)),
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

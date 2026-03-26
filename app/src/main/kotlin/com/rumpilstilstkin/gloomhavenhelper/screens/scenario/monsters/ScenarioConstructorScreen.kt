package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.monsters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomToolbarTitle
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomVariantCard
import com.rumpilstilstkin.gloomhavenhelper.ui.goods.GoodItem
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun ScenarioConstructorScreen(
    state: ScenarioConstructorStateUi,
    back: () -> Unit,
    toggleMonster: (String) -> Unit,
    startScenario: () -> Unit,
) {
    Scaffold(
        topBar = {
            GloomToolbarTitle(
                title = "Добавить монстров",
                back = back,
            )
        }
    ) { paddingValues ->

        when (state) {
            is ScenarioConstructorStateUi.Content -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp)
                ) {

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(vertical = 16.dp)
                    ) {
                        if (state.selectedMonsters.isNotEmpty()) {
                            item {
                                Text(
                                    text = "Выбранные",
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Spacer(
                                    modifier = Modifier.height(16.dp)
                                )
                            }
                            items(state.selectedMonsters) { monster ->
                                MonsterSelectCard(
                                    monster = monster,
                                    isSelected = true,
                                    onClick = { toggleMonster(monster) }
                                )
                            }
                        }
                        item {
                            Text(
                                text = "Доступные",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Spacer(
                                modifier = Modifier.height(16.dp)
                            )
                        }

                        items(state.availableMonsters) { monster ->
                            MonsterSelectCard(
                                monster = monster,
                                isSelected = false,
                                onClick = { toggleMonster(monster) }
                            )
                        }
                    }

                    Button(
                        onClick = startScenario,
                        enabled = state.selectedMonsters.isNotEmpty(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text("Добавить")
                    }

                }
            }

            ScenarioConstructorStateUi.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
private fun MonsterSelectCard(
    monster: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    GloomVariantCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        active = isSelected,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = monster,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ScenarioConstructorScreenPreview() {
    GloomhavenMasterTheme {
        ScenarioConstructorScreen(
            state = ScenarioConstructorStateUi.Content(
                availableMonsters = persistentListOf(
                    "Банит-страж",
                    "Темный маг",
                ),
                selectedMonsters = persistentListOf("Скелет-воин", "Зомби"),
            ),
            back = {},
            toggleMonster = {},
            startScenario = {},
        )
    }
}

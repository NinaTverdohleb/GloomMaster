package com.rumpilstilstkin.gloomhavenhelper.screens.scenario

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumpilstilstkin.gloomhavenhelper.navigation.events.GlHelperEventHelper
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ActionType
import com.rumpilstilstkin.gloomhavenhelper.screens.models.CardLine
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterAbilityCard
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterItem
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.screens.models.UnitStat
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.components.MonsterAbilityCardItem
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.components.MonsterUnitRow
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun ScenarioScreen(
    state: ScenarioUIState
) = Column {
    ScenarioHeader(
        title = state.name,
        addMonster = {}
    )
    ScenarioScreenContent(
        monsters = state.monsters,
        addUnit = {},
        changeUnitLife = { _ -> },
        modifier = Modifier
    )
}

@Composable
private fun ScenarioHeader(
    title: String,
    addMonster: () -> Unit,
    modifier: Modifier = Modifier
) = Row(
    modifier = modifier.padding(horizontal = 4.dp, vertical = 8.dp)
) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineLarge,
        modifier = modifier.weight(1f),
        textAlign = TextAlign.Center
    )
    IconButton(
        onClick = addMonster,
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Добавить монстра"
        )
    }
}

@Composable
fun ScenarioScreenContent(
    monsters: List<MonsterItem>,
    addUnit: (scenarioId: Int) -> Unit,
    changeUnitLife: (value: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (monsters.isEmpty()) {
            EmptyScenarioView(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            val pagerState = rememberPagerState(pageCount = { monsters.size })
            val currentMonster = monsters[pagerState.currentPage]

            Column(modifier = Modifier.fillMaxSize()) {
                HorizontalPager(
                    state = pagerState,
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    pageSpacing = 16.dp,
                    modifier = Modifier.padding(top = 48.dp)
                ) { page ->
                    val currentCard = monsters[page].currentCard
                    if (currentCard != null) {
                        MonsterAbilityCardItem(
                            card = currentCard,
                        )
                    } else {
                        // TODO empty card
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                MonsterUnitList(
                    monsterUnits = currentMonster.units,

                    addUnit = { addUnit(currentMonster.id) },
                    changeUnitLife = changeUnitLife,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}


@Composable
private fun MonsterUnitList(
    monsterUnits: List<MonsterUnit>,
    addUnit: () -> Unit,
    changeUnitLife: (value: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(monsterUnits, key = { it.number }) { unit ->
            MonsterUnitRow(
                unit = unit,
                changeUnitLife = changeUnitLife
            )
        }

        item {
            AddTaskButton(onClick = addUnit)
        }
    }
}

@Composable
fun AddTaskButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Добавить", color = MaterialTheme.colorScheme.primary, fontSize = 18.sp)
    }
}

@Composable
fun EmptyScenarioView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Добавьте монстров",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScenarioScreenPreview() {
    GloomhavenHalperTheme {
        ScenarioScreen(
            state = ScenarioUIState(
                name = "Гиблая лужa",
                monsters = listOf(
                    MonsterItem(
                        id = 1,
                        name = "Хвостожабка",
                        currentCard = MonsterAbilityCard(
                            id = "524",
                            name = "Разбойник страж",
                            initiative = 15,
                            lines = listOf(
                                CardLine.Action(ActionType.MOVE, "-1"),
                                CardLine.Action(
                                    ActionType.STRENGTH,
                                    "",
                                    CardLine.Text("Себя")
                                ),
                            ),
                            needsShuffle = true
                        ),
                        units = listOf(
                            MonsterUnit(
                                number = 2,
                                isSpecial = true,
                                currentLife = 10,
                                maxLife = 10,
                                stats = listOf(
                                    UnitStat(
                                        type = ActionType.MOVE,
                                        value = 3
                                    ),
                                    UnitStat(
                                        type = ActionType.ATTACK,
                                        value = 4
                                    ),
                                    UnitStat(
                                        type = ActionType.SHIELD,
                                        value = 2
                                    ),
                                )
                            ),
                        )
                    ),
                    MonsterItem(
                        id = 2,
                        name = "Скелет",
                        currentCard = MonsterAbilityCard(
                            id = "524",
                            name = "Разбойник страж",
                            initiative = 15,
                            lines = listOf(
                                CardLine.Action(ActionType.MOVE, "-1"),
                                CardLine.Action(
                                    ActionType.STRENGTH,
                                    "",
                                    CardLine.Text("Себя")
                                ),
                            ),
                            needsShuffle = true
                        ),
                        units = listOf(
                            MonsterUnit(
                                number = 2,
                                isSpecial = true,
                                currentLife = 10,
                                maxLife = 10,
                                stats = listOf(
                                    UnitStat(
                                        type = ActionType.MOVE,
                                        value = 3
                                    ),
                                    UnitStat(
                                        type = ActionType.ATTACK,
                                        value = 4
                                    ),
                                    UnitStat(
                                        type = ActionType.SHIELD,
                                        value = 2
                                    ),
                                )
                            ),
                        )
                    )
                )
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScenarioScreenEmptyPreview() {
    GloomhavenHalperTheme {
        ScenarioScreen(
            state = ScenarioUIState(
                name = "Гиблая лужa",
            )
        )
    }
}

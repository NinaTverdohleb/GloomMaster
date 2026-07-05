package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons.FabContextMenuItem
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons.GloomButton
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons.GloomFabWithContextMenu
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.empty.EmptyView
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.toolbar.GloomToolbarAction
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.EmptyIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Magic
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ChargeLevel
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components.PageIndicator
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components.RegularMonsterCard
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components.ScenarioHeader
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components.SpinningNumberOverlay
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.MonsterItemUi
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.ScenarioStateUi
import com.rumpilstilstkin.gloomhavenhelper.testtags.screens.scenario.play.PlayScenarioScreenTestTags
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf

@Composable
internal fun ScenarioScreen(
    state: ScenarioStateUi,
    complete: () -> Unit,
    showStats: () -> Unit,
    back: () -> Unit,
    addScenarioMonsters: () -> Unit,
    addNewMonsters: () -> Unit,
    deleteMonster: (monsterSlug: String) -> Unit,
    deleteUnit: (unitNumber: Int, monsterSlug: String) -> Unit,
    updateUnitLife: (unitNumber: Int, monsterSlug: String, life: Int) -> Unit,
    switchUnitEffect: (unitNumber: Int, monsterSlug: String, effect: MonsterStatType) -> Unit,
    nextRound: () -> Unit,
    addMonsterUnits: (monsterSlug: String) -> Unit,
    clickMagic: (magic: Magic) -> Unit,
    onLevel: (unitNumber: Int, monsterSlug: String) -> Unit,
) = Scaffold(
    topBar = {
        GloomToolbarAction(
            title = stringResource(R.string.round_status, state.round),
            back = back,
            backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
            actionClick = complete,
            actionIcon = AppIcon.Check,
        )
    },
    floatingActionButtonPosition = FabPosition.End,
    floatingActionButton = {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.End,
        ) {
            var expanded by remember { mutableStateOf(false) }

            GloomFabWithContextMenu(
                icon = AppIcon.Plus,
                expanded = expanded,
                onClick = { expanded = !expanded },
                modifier = Modifier.testTag(PlayScenarioScreenTestTags.ADD_MONSTER_FAB),
            ) {
                if (state.monstersForAdd.isNotEmpty()) {
                    FabContextMenuItem(
                        icon = AppIcon.Check,
                        text = stringResource(R.string.add_scenario_enemies),
                        isError = false,
                        modifier = Modifier.testTag(PlayScenarioScreenTestTags.ADD_MONSTER_FAB_SCENARIO),
                        onClick = {
                            addScenarioMonsters()
                            expanded = false
                        },
                    )
                }

                FabContextMenuItem(
                    icon = AppIcon.Plus,
                    text = stringResource(R.string.add_new_enemies),
                    isError = false,
                    modifier = Modifier.testTag(PlayScenarioScreenTestTags.ADD_MONSTER_FAB_ALL),
                    onClick = {
                        addNewMonsters()
                        expanded = false
                    },
                )
            }
            GloomButton(
                text = stringResource(R.string.round_label),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .testTag(PlayScenarioScreenTestTags.ROUND_BUTTON),
                onClick = nextRound,
            )
        }
    },
) { paddingValues ->
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(bottom = 80.dp)
                .clipToBounds(),
    ) {
        var roundAnimationVisible by remember { mutableStateOf(false) }

        LaunchedEffect(state.round) {
            if (state.round != 0) {
                roundAnimationVisible = true
            }
        }
        Column(modifier = Modifier.fillMaxSize()) {
            ScenarioHeader(
                magics = state.magicChargeList,
                clickMagic = clickMagic,
                trapDamage = state.trapDamage,
                showStats = showStats,
            )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline,
            )
            ScenarioScreenContent(
                modifier = Modifier.weight(1f),
                monsters = state.monsters,
                delete = deleteMonster,
                deleteUnit = deleteUnit,
                updateUnitLife = updateUnitLife,
                switchUnitEffect = switchUnitEffect,
                addMonsterUnits = addMonsterUnits,
                onLevel = onLevel,
                round = state.round,
            )
        }

        SpinningNumberOverlay(
            visible = roundAnimationVisible,
            targetNumber = state.smallestInitiative,
            spinId = state.round,
            onFinished = { roundAnimationVisible = false },
        )
    }
}

@Composable
fun ScenarioScreenContent(
    round: Int,
    monsters: ImmutableList<MonsterItemUi>,
    modifier: Modifier = Modifier,
    delete: (monsterSlug: String) -> Unit,
    deleteUnit: (unitNumber: Int, monsterSlug: String) -> Unit,
    updateUnitLife: (unitNumber: Int, monsterSlug: String, life: Int) -> Unit,
    switchUnitEffect: (unitNumber: Int, monsterSlug: String, effect: MonsterStatType) -> Unit,
    addMonsterUnits: (monsterSlug: String) -> Unit,
    onLevel: (unitNumber: Int, monsterSlug: String) -> Unit,
) = Column(
    modifier =
        modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
) {
    if (monsters.isEmpty()) {
        EmptyView(
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            icon = EmptyIcon.Enemy,
            title = stringResource(R.string.empty_enemies_title),
            description = stringResource(R.string.empty_enemy_description),
        )
    } else {
        val pageCount = monsters.size
        val pagerState = rememberPagerState(pageCount = { pageCount })

        LaunchedEffect(round) {
            if (pagerState.currentPage != 0) {
                pagerState.animateScrollToPage(0)
            }
        }

        PageIndicator(pagerState)
        HorizontalPager(
            modifier = Modifier.testTag(PlayScenarioScreenTestTags.MONSTER_PAGER),
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            pageSpacing = 16.dp,
        ) { page ->
            val scrollState = rememberScrollState()
            LaunchedEffect(round) {
                scrollState.scrollTo(0)
            }
            Box(modifier = Modifier.fillMaxSize()) {
                val monster = monsters[page]
                RegularMonsterCard(
                    scrollState = scrollState,
                    modifier = Modifier.testTag(PlayScenarioScreenTestTags.card(page)),
                    item = monster,
                    delete = delete,
                    deleteUnit = deleteUnit,
                    updateUnitLife = updateUnitLife,
                    switchUnitEffect = switchUnitEffect,
                    addMonsterUnits = addMonsterUnits,
                    onLevel = onLevel,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScenarioScreenPreview() {
    GloomhavenMasterTheme {
        ScenarioScreen(
            state =
                ScenarioStateUi(
                    scenarioName = "Bad place",
                    monsters =
                        persistentListOf(
                            MonsterItemUi.fixture(),
                        ),
                    magicChargeList =
                        persistentMapOf(
                            Magic.FIRE to ChargeLevel.Zero,
                            Magic.FROST to ChargeLevel.Two,
                            Magic.AIR to ChargeLevel.Zero,
                            Magic.EARTH to ChargeLevel.Two,
                            Magic.SUN to ChargeLevel.One,
                            Magic.MOON to ChargeLevel.Two,
                        ),
                ),
            addScenarioMonsters = {},
            addNewMonsters = {},
            back = {},
            complete = {},
            showStats = {},
            deleteMonster = { _ -> },
            deleteUnit = { _, _ -> },
            updateUnitLife = { _, _, _ -> },
            switchUnitEffect = { _, _, _ -> },
            nextRound = {},
            addMonsterUnits = { },
            clickMagic = {},
            onLevel = { _, _ -> },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScenarioScreenEmptyPreview() {
    GloomhavenMasterTheme {
        ScenarioScreen(
            state =
                ScenarioStateUi(
                    scenarioName = "Bad place",
                ),
            addScenarioMonsters = {},
            addNewMonsters = {},
            back = {},
            complete = {},
            showStats = {},
            deleteMonster = { _ -> },
            deleteUnit = { _, _ -> },
            updateUnitLife = { _, _, _ -> },
            switchUnitEffect = { _, _, _ -> },
            nextRound = {},
            addMonsterUnits = { },
            clickMagic = {},
            onLevel = { _, _ -> },
        )
    }
}

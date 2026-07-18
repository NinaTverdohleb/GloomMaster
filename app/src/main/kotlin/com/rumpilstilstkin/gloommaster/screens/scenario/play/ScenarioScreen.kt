package com.rumpilstilstkin.gloommaster.screens.scenario.play

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.rumpilstilstkin.gloommaster.R
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.FabContextMenuItem
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.GloomButton
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.GloomFab
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.GloomFabWithContextMenu
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.GloomOutlineButtonIcon
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.GloomOutlineButtonIconVariant
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.GloomOutlineFilledButtonIcon
import com.rumpilstilstkin.gloommaster.designsystem.components.buttons.GloomOutlineFilledButtonIconVariant
import com.rumpilstilstkin.gloommaster.designsystem.components.empty.EmptyView
import com.rumpilstilstkin.gloommaster.designsystem.components.empty.EmptyViewWithAction
import com.rumpilstilstkin.gloommaster.designsystem.components.toolbar.GloomToolbarAction
import com.rumpilstilstkin.gloommaster.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloommaster.designsystem.icons.EmptyIcon
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.domain.entity.Magic
import com.rumpilstilstkin.gloommaster.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloommaster.domain.entity.scenario.ChargeLevel
import com.rumpilstilstkin.gloommaster.screens.scenario.play.components.MonstersPageIndicator
import com.rumpilstilstkin.gloommaster.screens.scenario.play.components.RegularMonsterCard
import com.rumpilstilstkin.gloommaster.screens.scenario.play.components.ScenarioHeader
import com.rumpilstilstkin.gloommaster.screens.scenario.play.components.SpinningNumberOverlay
import com.rumpilstilstkin.gloommaster.screens.scenario.play.state.MonsterItemUi
import com.rumpilstilstkin.gloommaster.screens.scenario.play.state.ScenarioStateUi
import com.rumpilstilstkin.gloommaster.testtags.screens.scenario.play.PlayScenarioScreenTestTags
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf

@Composable
internal fun ScenarioScreen(
    state: ScenarioStateUi,
    complete: () -> Unit,
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
    openRandom: () -> Unit,
) = Scaffold(
    topBar = {
        GloomToolbarAction(
            title = stringResource(R.string.round_status, state.round),
            back = back,
            backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
            actions = {
                IconButton(onClick = complete) {
                    Icon(
                        AppIcon.Settings.painter(),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            },
        )
    },
    floatingActionButtonPosition = FabPosition.End,
    floatingActionButton = {
        Column(
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
        }
    },
) { paddingValues ->
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                nextRound = nextRound,
                openRandom = openRandom,
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
                nextRound = nextRound,
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
    nextRound: () -> Unit,
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
    verticalArrangement = Arrangement.spacedBy(12.dp),
) {
    if (monsters.isEmpty()) {
        EmptyViewWithAction(
            modifier =
                Modifier
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

        MonstersPageIndicator(
            modifier = Modifier.fillMaxWidth(),
            monsters = monsters,
            pageState = pagerState,
        )

        HorizontalPager(
            modifier = Modifier.testTag(PlayScenarioScreenTestTags.MONSTER_PAGER),
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            pageSpacing = 16.dp,
            key = { page -> monsters[page].slug },
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
            deleteMonster = { _ -> },
            deleteUnit = { _, _ -> },
            updateUnitLife = { _, _, _ -> },
            switchUnitEffect = { _, _, _ -> },
            nextRound = {},
            addMonsterUnits = { },
            clickMagic = {},
            onLevel = { _, _ -> },
            openRandom = {},
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
            deleteMonster = { _ -> },
            deleteUnit = { _, _ -> },
            updateUnitLife = { _, _, _ -> },
            switchUnitEffect = { _, _, _ -> },
            nextRound = {},
            addMonsterUnits = { },
            clickMagic = {},
            onLevel = { _, _ -> },
            openRandom = {},
        )
    }
}

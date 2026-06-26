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
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons.GloomButton
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons.GloomFab
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.empty.EmptyView
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.toolbar.GloomToolbarAction
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.EmptyIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Magic
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.ChargeLevel
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components.PageIndicator
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components.RegularMonsterCard
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components.ScenarioHeader
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.MonsterItemUi
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.ScenarioStateUi
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toPersistentSet

@Composable
internal fun ScenarioScreen(
    state: ScenarioStateUi,
    complete: () -> Unit,
    showStats: () -> Unit,
    back: () -> Unit,
    addMonster: () -> Unit,
    deleteMonster: (monsterSlug: String) -> Unit,
    deleteUnit: (unitNumber: Int, monsterSlug: String) -> Unit,
    updateUnitLife: (unitNumber: Int, monsterSlug: String, life: Int) -> Unit,
    switchUnitEffect: (unitNumber: Int, monsterSlug: String, effect: MonsterStatType) -> Unit,
    nextRound: () -> Unit,
    addMonsterUnits: (unitNumbers: List<Int>, monsterSlug: String, monsterName: String) -> Unit,
    clickMagic: (magic: Magic) -> Unit,
    onLevel: (unit: MonsterUnit, monsterSlug: String) -> Unit,
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
            horizontalAlignment = Alignment.End
        ) {
            GloomFab(
                icon = AppIcon.Plus,
                onClick = addMonster
            )
            GloomButton(
                text = stringResource(R.string.round_label),
                modifier = Modifier.fillMaxWidth(),
                onClick = nextRound,
            )
        }
    }
) { paddingValues ->
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(bottom = 80.dp),
    ) {
        ScenarioHeader(
            scenarioNumber = state.scenarioNumber,
            scenarioName = state.scenarioName.ifBlank { stringResource(R.string.custom_scenario) },
            location = state.scenarioLocation,
            magics = state.magicChargeList,
            clickMagic = clickMagic,
            trapDamage = state.trapDamage,
            showStats = showStats,
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline
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
            availableEffects = state.availableEffects,
        )
    }
}

@Composable
fun ScenarioScreenContent(
    availableEffects: ImmutableSet<MonsterStatType>,
    monsters: List<MonsterItemUi>,
    delete: (monsterSlug: String) -> Unit,
    deleteUnit: (unitNumber: Int, monsterSlug: String) -> Unit,
    updateUnitLife: (unitNumber: Int, monsterSlug: String, life: Int) -> Unit,
    switchUnitEffect: (unitNumber: Int, monsterSlug: String, effect: MonsterStatType) -> Unit,
    addMonsterUnits: (unitNumbers: List<Int>, monsterSlug: String, monsterName: String) -> Unit,
    onLevel: (unit: MonsterUnit, monsterSlug: String) -> Unit,
    modifier: Modifier = Modifier,
) = Column(
    modifier = modifier
        .fillMaxWidth()
        .padding(top = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
) {
    if (monsters.isEmpty()) {
        EmptyView(
            icon = EmptyIcon.Enemy,
            title = stringResource(R.string.empty_enemies_title),
            description = stringResource(R.string.empty_enemy_description)
        )

    } else {
        val pageCount = monsters.size
        val pagerState = rememberPagerState(pageCount = { pageCount })
        PageIndicator(pagerState)
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            pageSpacing = 16.dp,
        ) { page ->
            Box(modifier = Modifier.fillMaxSize()) {
                val monster = monsters[page]
                RegularMonsterCard(
                    item = monster,
                    delete = delete,
                    deleteUnit = deleteUnit,
                    updateUnitLife = updateUnitLife,
                    switchUnitEffect = switchUnitEffect,
                    addMonsterUnits = addMonsterUnits,
                    onLevel = onLevel,
                    availableEffects = availableEffects,
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
                    availableEffects = MonsterStatType.mainEffectsPack.toPersistentSet(),
                ),
            addMonster = {},
            back = {},
            complete = {},
            showStats = {},
            deleteMonster = { _ -> },
            deleteUnit = { _, _ -> },
            updateUnitLife = { _, _, _ -> },
            switchUnitEffect = { _, _, _ -> },
            nextRound = {},
            addMonsterUnits = { _, _, _ -> },
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
                    availableEffects = MonsterStatType.mainEffectsPack.toPersistentSet(),
                ),
            addMonster = {},
            back = {},
            complete = {},
            showStats = {},
            deleteMonster = { _ -> },
            deleteUnit = { _, _ -> },
            updateUnitLife = { _, _, _ -> },
            switchUnitEffect = { _, _, _ -> },
            nextRound = {},
            addMonsterUnits = { _, _, _ -> },
            clickMagic = {},
            onLevel = { _, _ -> },
        )
    }
}

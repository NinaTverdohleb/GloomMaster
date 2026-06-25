package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play

import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons.GloomButton
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.buttons.GloomFab
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.toolbar.GloomToolbar
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.toolbar.GloomToolbarAction
import com.rumpilstilstkin.gloomhavenhelper.designsystem.icons.AppIcon
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Magic
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components.AddMonsterCard
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components.PageIndicator
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components.RegularMonsterCard
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components.ScenarioHeader
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.ScenarioStateUi
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf

@Composable
internal fun ScenarioScreen(
    state: ScenarioStateUi,
    complete: () -> Unit,
    back: () -> Unit,
    addMonster: () -> Unit,
    deleteMonster: (monsterSlug: String) -> Unit,
    deleteUnit: (unitNumber: Int, monsterSlug: String) -> Unit,
    updateUnitLife: (unitNumber: Int, monsterSlug: String, life: Int) -> Unit,
    switchUnitEffect: (unitNumber: Int, monsterSlug: String, effect: MonsterStatType) -> Unit,
    nextRound: () -> Unit,
    addMonsterUnit: (unitNumbers: List<Int>, monsterSlug: String, isElite: Boolean) -> Unit,
    clickMagic: (magic: Magic) -> Unit,
    changeUnitLevel: (monsterSlug: String, unit: MonsterUnit, level: Int) -> Unit,
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
) { paddingValues ->
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(paddingValues),
    ) {
        ScenarioHeader(
            scenarioNumber = state.scenarioNumber,
            scenarioName = state.scenarioName.ifBlank { stringResource(R.string.custom_scenario) },
            location = state.scenarioLocation,
            magics = state.magicChargeList,
            clickMagic = clickMagic,
            trapDamage = state.trapDamage,
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
        ScenarioScreenContent(
            modifier = Modifier.weight(1f),
            monsters = state.monsters,
            addMonster = addMonster,
            delete = deleteMonster,
            deleteUnit = deleteUnit,
            updateUnitLife = updateUnitLife,
            switchUnitEffect = switchUnitEffect,
            addMonsterUnit = addMonsterUnit,
            changeUnitLevel = changeUnitLevel,
            availableEffects = state.availableEffects,
        )
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
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
}

@Composable
fun ScenarioScreenContent(
    availableEffects: Set<MonsterStatType>,
    monsters: List<MonsterItem>,
    addMonster: () -> Unit,
    delete: (monsterSlug: String) -> Unit,
    deleteUnit: (unitNumber: Int, monsterSlug: String) -> Unit,
    updateUnitLife: (unitNumber: Int, monsterSlug: String, life: Int) -> Unit,
    switchUnitEffect: (unitNumber: Int, monsterSlug: String, effect: MonsterStatType) -> Unit,
    addMonsterUnit: (unitNumbers: List<Int>, monsterSlug: String, isElite: Boolean) -> Unit,
    changeUnitLevel: (monsterSlug: String, unit: MonsterUnit, level: Int) -> Unit,
    modifier: Modifier = Modifier,
) = Column(
    modifier = modifier
        .fillMaxWidth()
        .padding(top = 24.dp),
    verticalArrangement = Arrangement.spacedBy(24.dp)
) {
    val pageCount = monsters.size
    val pagerState = rememberPagerState(pageCount = { pageCount })
    PageIndicator(pagerState)
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 16.dp),
        pageSpacing = 16.dp,
    ) { page ->
        Box(modifier = Modifier.fillMaxSize()) {
            val currentCard = monsters[page]
            RegularMonsterCard(
                item = currentCard,
                delete = delete,
                deleteUnit = deleteUnit,
                updateUnitLife = updateUnitLife,
                switchUnitEffect = switchUnitEffect,
                addMonsterUnit = addMonsterUnit,
                changeUnitLevel = changeUnitLevel,
                availableEffects = availableEffects,
            )
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
                            MonsterItem.fixture(),
                        ),
                    magicChargeList =
                        persistentMapOf(
                            Magic.FIRE to 0,
                            Magic.FROST to 2,
                            Magic.AIR to 0,
                            Magic.EARTH to 2,
                            Magic.SUN to 1,
                            Magic.MOON to 2,
                        ),
                    availableEffects = MonsterStatType.mainEffectsPack,
                ),
            addMonster = {},
            back = {},
            complete = {},
            deleteMonster = { _ -> },
            deleteUnit = { _, _ -> },
            updateUnitLife = { _, _, _ -> },
            switchUnitEffect = { _, _, _ -> },
            nextRound = {},
            addMonsterUnit = { _, _, _ -> },
            clickMagic = {},
            changeUnitLevel = { _, _, _ -> },
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
                    availableEffects = MonsterStatType.mainEffectsPack,
                ),
            addMonster = {},
            back = {},
            complete = {},
            deleteMonster = { _ -> },
            deleteUnit = { _, _ -> },
            updateUnitLife = { _, _, _ -> },
            switchUnitEffect = { _, _, _ -> },
            nextRound = {},
            addMonsterUnit = { _, _, _ -> },
            clickMagic = {},
            changeUnitLevel = { _, _, _ -> },
        )
    }
}

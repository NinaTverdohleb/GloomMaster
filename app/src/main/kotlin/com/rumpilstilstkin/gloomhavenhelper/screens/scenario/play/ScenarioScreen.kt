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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ActionUi
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterItem
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components.AddMonsterCard
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components.RegularMonsterCard
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components.ScenarioHeader
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.Magic
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.MagicValue
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.ScenarioStateUi
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomToolbarStatus
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf

@Composable
internal fun ScenarioScreen(
    state: ScenarioStateUi,
    complete: () -> Unit,
    back: () -> Unit,
    addMonster: () -> Unit,
    deleteMonster: (monsterId: Int) -> Unit,
    deleteUnit: (unitNumber: Int, monsterId: Int) -> Unit,
    updateUnitLife: (unitNumber: Int, monsterId: Int, life: Int) -> Unit,
    switchUnitEffect: (unitNumber: Int, monsterId: Int, effect: ActionUi) -> Unit,
    nextRound: () -> Unit,
    addMonsterUnit: (unitNumbers: List<Int>, monsterId: Int, isElite: Boolean) -> Unit,
    clickMagic: (magic: Magic) -> Unit,
    changeUnitLevel: (monsterId: Int, unit: MonsterUnit, level: Int) -> Unit,
    ) = Scaffold(
    topBar = {
        CombatToolbar(
            roundNumber = state.round,
            back = back,
            complete = complete
        )
    },
) { paddingValues ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(top = 16.dp),
    ) {
        ScenarioHeader(
            title = state.name,
            magics = state.magicChargeList,
            clickMagic = clickMagic,
            level = state.level,
            exp = state.exp,
            gold = state.gold,
            trapDamage = state.trapDamage
        )

        Spacer(
            modifier = Modifier.height(8.dp)
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
            changeUnitLevel = changeUnitLevel
        )
        if (state.monsters.isNotEmpty()) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = nextRound,
                contentPadding = PaddingValues(16.dp)
            ) {
                Text(
                    text = "Раунд",
                    fontSize = 16.sp
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CombatToolbar(
    roundNumber: Int,
    complete: () -> Unit,
    back: () -> Unit
) = GloomToolbarStatus(
    status = "Раунд: $roundNumber",
    back = back,
    actions = {
        IconButton(onClick = complete) {
            Icon(
                Icons.Default.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
)

@Composable
fun ScenarioScreenContent(
    monsters: List<MonsterItem>,
    addMonster: () -> Unit,
    delete: (monsterNumber: Int) -> Unit,
    deleteUnit: (unitNumber: Int, monsterId: Int) -> Unit,
    updateUnitLife: (unitNumber: Int, monsterId: Int, life: Int) -> Unit,
    switchUnitEffect: (unitNumber: Int, monsterId: Int, effect: ActionUi) -> Unit,
    addMonsterUnit: (unitNumbers: List<Int>, monsterId: Int, isElite: Boolean) -> Unit,
    changeUnitLevel: (monsterId: Int, unit: MonsterUnit, level: Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val pageCount = monsters.size + 1
    val pagerState = rememberPagerState(pageCount = { pageCount })
    Row(
        Modifier
            .height(10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)

            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(8.dp)
            )
        }
    }
    HorizontalPager(
        modifier = modifier.padding(top = 24.dp),
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 16.dp),
        pageSpacing = 16.dp,
    ) { page ->
        Box(modifier = modifier.fillMaxSize()) {
            if (page == pageCount - 1) {
                AddMonsterCard(
                    addMonster = addMonster
                )
            } else {
                val currentCard = monsters[page]
                RegularMonsterCard(
                    item = currentCard,
                    delete = delete,
                    deleteUnit = deleteUnit,
                    updateUnitLife = updateUnitLife,
                    switchUnitEffect = switchUnitEffect,
                    addMonsterUnit = addMonsterUnit,
                    changeUnitLevel = changeUnitLevel
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
            state = ScenarioStateUi(
                name = "Гиблая лужa",
                monsters = persistentListOf(
                    MonsterItem.fixture()
                ),
                magicChargeList = persistentMapOf(
                    Magic.FIRE to MagicValue(0),
                    Magic.FROST to MagicValue(2),
                    Magic.AIR to MagicValue(0),
                    Magic.EARTH to MagicValue(2),
                    Magic.SUN to MagicValue(1),
                    Magic.MOON to MagicValue(2),
                )
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
            state = ScenarioStateUi(
                name = "Гиблая лужa",
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

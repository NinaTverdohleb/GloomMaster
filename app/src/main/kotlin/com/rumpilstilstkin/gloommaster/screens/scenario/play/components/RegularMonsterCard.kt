package com.rumpilstilstkin.gloommaster.screens.scenario.play.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloommaster.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloommaster.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloommaster.screens.scenario.play.state.MonsterItemUi
import com.rumpilstilstkin.gloommaster.screens.scenario.play.state.MonsterUnitUi
import com.rumpilstilstkin.gloommaster.screens.scenario.play.state.UnitCompact
import kotlinx.collections.immutable.persistentMapOf

@Composable
fun RegularMonsterCard(
    item: MonsterItemUi,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
    delete: (monsterSlug: String) -> Unit,
    deleteUnit: (unitNumber: Int, monsterSlug: String) -> Unit,
    updateUnitLife: (unitNumber: Int, monsterSlug: String, life: Int) -> Unit,
    switchUnitEffect: (unitNumber: Int, monsterSlug: String, effect: MonsterStatType) -> Unit,
    addMonsterUnits: (monsterSlug: String) -> Unit,
    onLevel: (unitNumber: Int, monsterSlug: String) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        MonsterCard(
            card = item.currentCard,
            name = item.name,
            isFly = item.isFly,
            delete = { delete(item.slug) },
            onAddUnit = { addMonsterUnits(item.slug) },
        )
        if (item.units.isNotEmpty()) {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                var selected: UnitCompact by remember(item.units.keys) {
                    mutableStateOf(item.units.keys.first())
                }
                RegularMonsterUnit(
                    unit = item.units.getValue(selected),
                    changeLife = { life ->
                        updateUnitLife(
                            selected.number,
                            item.slug,
                            life,
                        )
                    },
                    switchEffect = { effect ->
                        switchUnitEffect(
                            selected.number,
                            item.slug,
                            effect,
                        )
                    },
                    deleteUnit = {
                        deleteUnit(
                            selected.number,
                            item.slug,
                        )
                    },
                    levelClick = {
                        onLevel(
                            selected.number,
                            item.slug,
                        )
                    },
                )
                UnitSelector(units = item.units.keys) { unit ->
                    selected = unit
                }
                Spacer(Modifier.height(48.dp))
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1C24)
@Composable
private fun RegularMonsterCardPreview() {
    GloomhavenMasterTheme {
        RegularMonsterCard(
            item =
                MonsterItemUi(
                    slug = "1",
                    isBoss = true,
                    name = "Living Bones",
                    currentCard = null,
                    isFly = true,
                    units =
                        persistentMapOf(
                            UnitCompact(1, false) to MonsterUnitUi.fixture(1),
                        ),
                ),
            delete = {},
            deleteUnit = { _, _ -> },
            updateUnitLife = { _, _, _ -> },
            switchUnitEffect = { _, _, _ -> },
            addMonsterUnits = { },
            onLevel = { _, _ -> },
        )
    }
}

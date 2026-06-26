package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.MonsterItemUi
import com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state.UnitCompact
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toPersistentSet

@Composable
fun RegularMonsterCard(
    item: MonsterItemUi,
    availableEffects: ImmutableSet<MonsterStatType>,
    delete: (monsterSlug: String) -> Unit,
    deleteUnit: (unitNumber: Int, monsterSlug: String) -> Unit,
    updateUnitLife: (unitNumber: Int, monsterSlug: String, life: Int) -> Unit,
    switchUnitEffect: (unitNumber: Int, monsterSlug: String, effect: MonsterStatType) -> Unit,
    addMonsterUnits: (unitNumbers: List<Int>, monsterSlug: String, monsterName: String) -> Unit,
    onLevel: (unit: MonsterUnit, monsterSlug: String) -> Unit,
) {
    val existingNumbers = item.units.keys.map { it.number }.toSet()
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MonsterCard(
            card = item.currentCard,
            name = item.name,
            isFly = item.isFly,
            delete = { delete(item.slug) },
            onAddUnit =
                if (item.isBoss) {
                    null
                } else {
                    {
                        addMonsterUnits(
                            (1..15).toList().filter { it !in existingNumbers },
                            item.slug,
                            item.name,
                        )
                    }
                },
        )
        if (item.units.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            ) {
                UnitSelector(
                    units = item.units.keys,
                    cardContent = { unitNumber ->
                        item.units[unitNumber]?.let {
                            RegularMonsterUnit(
                                unit = it,
                                changeLife = { _, life ->
                                    updateUnitLife(
                                        unitNumber.number,
                                        item.slug,
                                        life
                                    )
                                },
                                switchEffect = { _, effect ->
                                    switchUnitEffect(
                                        unitNumber.number,
                                        item.slug,
                                        effect
                                    )
                                },
                                deleteUnit = {
                                    deleteUnit(
                                        unitNumber.number,
                                        item.slug
                                    )
                                },
                                levelClick = { unit ->
                                    onLevel(
                                        unit,
                                        item.slug
                                    )
                                },
                                effects = availableEffects,
                            )
                        }
                    })
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
                            UnitCompact(1, false) to MonsterUnit.fixture(1),
                        ),
                ),
            availableEffects = MonsterStatType.mainEffectsPack.toPersistentSet(),
            delete = {},
            deleteUnit = { _, _ -> },
            updateUnitLife = { _, _, _ -> },
            switchUnitEffect = { _, _, _ -> },
            addMonsterUnits = { _, _, _ -> },
            onLevel = { _, _ -> },
        )
    }
}

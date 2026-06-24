package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components

import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rumpilstilstkin.gloomhavenhelper.R
import com.rumpilstilstkin.gloomhavenhelper.designsystem.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.designsystem.theme.GloomhavenMasterTheme
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterItem
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterUnit
import kotlinx.collections.immutable.persistentListOf

@Composable
fun RegularMonsterCard(
    item: MonsterItem,
    availableEffects: Set<MonsterStatType>,
    modifier: Modifier = Modifier,
    delete: (monsterSlug: String) -> Unit,
    deleteUnit: (unitNumber: Int, monsterSlug: String) -> Unit,
    updateUnitLife: (unitNumber: Int, monsterSlug: String, life: Int) -> Unit,
    switchUnitEffect: (unitNumber: Int, monsterSlug: String, effect: MonsterStatType) -> Unit,
    addMonsterUnit: (unitNumbers: List<Int>, monsterSlug: String, isSpecial: Boolean) -> Unit,
    changeUnitLevel: (monsterSlug: String, unit: MonsterUnit, level: Int) -> Unit,
) {
    var showSpawnDialog by remember { mutableStateOf(false) }
    var selectedUnit by remember { mutableStateOf<MonsterUnit?>(null) }

    GloomCard(modifier) {
        MonsterCardHeader(
            name = item.name,
            isFly = item.isFly,
            delete = { delete(item.slug) },
            onAddUnit =
                if (item.isBoss) {
                    null
                } else {
                    { showSpawnDialog = true }
                },
        )
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                MonsterActionCard(
                    monsterName = item.name,
                    card = item.currentCard,
                )
            }
            item {
                Text(
                    text = stringResource(R.string.active_enemies),
                    fontSize = 9.sp,
                    letterSpacing = 1.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 2.dp),
                )
            }
            if (item.units.isEmpty()) {
                item {
                    EmptyMonsterUnitCard()
                }
            } else {
                items(item.units, key = { it.number }) { unit ->
                    MonsterUnitCard(
                        modifier =
                            Modifier
                                .animateItem(
                                    fadeOutSpec = tween(400),
                                    placementSpec = spring(),
                                ),
                        unit = unit,
                        availableEffects = availableEffects,
                        isBoss = item.isBoss,
                        deleteUnit = { unitNumber -> deleteUnit(unitNumber, item.slug) },
                        switchEffect = { unitNumber, effect ->
                            switchUnitEffect(
                                unitNumber,
                                item.slug,
                                effect,
                            )
                        },
                        changeLife = { unitNumber, life ->
                            updateUnitLife(
                                unitNumber,
                                item.slug,
                                life,
                            )
                        },
                        levelClick = {
                            selectedUnit = it
                        },
                    )
                }
            }
        }
    }

    selectedUnit?.let {
        MonsterLevelDialog(
            unitLevel = it.level,
            monsterName = item.name,
            dismiss = { selectedUnit = null },
            changeLevel = { level ->
                changeUnitLevel(
                    item.slug,
                    it,
                    level,
                )
                selectedUnit = null
            },
        )
    }

    if (showSpawnDialog) {
        val existingNumbers = item.units.map { it.number }.toSet()
        SpawnMonsterDialog(
            monsterName = item.name,
            availableIds = (1..14).toList().filter { it !in existingNumbers },
            onDismiss = { showSpawnDialog = false },
            onSpawn = { numbers, special ->
                addMonsterUnit(numbers, item.slug, special)
                showSpawnDialog = false
            },
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1C24)
@Composable
private fun RegularMonsterCardPreview() {
    GloomhavenMasterTheme {
        RegularMonsterCard(
            item =
                MonsterItem(
                    slug = "1",
                    isBoss = true,
                    name = "Living Bones",
                    currentCard = null,
                    isFly = true,
                    units =
                        persistentListOf(
                            MonsterUnit.fixture(1),
                        ),
                    deck = "",
                ),
            availableEffects = MonsterStatType.mainEffectsPack,
            delete = {},
            deleteUnit = { _, _ -> },
            updateUnitLife = { _, _, _ -> },
            switchUnitEffect = { _, _, _ -> },
            addMonsterUnit = { _, _, _ -> },
            changeUnitLevel = { _, _, _ -> },
        )
    }
}

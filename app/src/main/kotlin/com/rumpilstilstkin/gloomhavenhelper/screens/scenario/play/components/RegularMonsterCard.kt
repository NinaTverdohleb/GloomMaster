package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rumpilstilstkin.gloomhavenhelper.screens.models.ActionUi
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterItem
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.ui.components.GloomCard
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenMasterTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun RegularMonsterCard(
    item: MonsterItem,
    modifier: Modifier = Modifier,
    delete: (monsterNumber: Int) -> Unit,
    deleteUnit: (unitNumber: Int, monsterId: Int) -> Unit,
    updateUnitLife: (unitNumber: Int, monsterId: Int, life: Int) -> Unit,
    switchUnitEffect: (unitNumber: Int, monsterId: Int, effect: ActionUi) -> Unit,
    addMonsterUnit: (unitNumbers: List<Int>, monsterId: Int, isSpecial: Boolean) -> Unit,
    changeUnitLevel: (monsterId: Int, unit: MonsterUnit, level: Int) -> Unit
) {
    var showSpawnDialog by remember { mutableStateOf(false) }
    var selectedUnit by remember { mutableStateOf<MonsterUnit?>(null) }

    GloomCard(modifier) {
        MonsterCardHeader(
            name = item.name,
            isFly = item.isFly,
            delete = { delete(item.id) },
            onAddUnit = if (item.isBoss) {
                null
            } else {
                { showSpawnDialog = true }
            }
        )
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                MonsterActionCard(
                    card = item.currentCard
                )
            }
            item {
                Text(
                    text = "Активные враги",
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
                        unit = unit,
                        isBoss = item.isBoss,
                        deleteUnit = { unitNumber -> deleteUnit(unitNumber, item.id) },
                        switchEffect = { unitNumber, effect ->
                            switchUnitEffect(
                                unitNumber,
                                item.id,
                                effect
                            )
                        },
                        changeLife = { unitNumber, life ->
                            updateUnitLife(
                                unitNumber,
                                item.id,
                                life
                            )
                        },
                        levelClick = {
                            selectedUnit = it
                        }
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
                    item.id,
                    it,
                    level
                )
                selectedUnit = null
            }
        )
    }

    if (showSpawnDialog) {
        val existingNumbers = item.units.map { it.number }.toSet()
        SpawnMonsterDialog(
            monsterName = item.name,
            availableIds = (1..14).toList().filter { it !in existingNumbers },
            onDismiss = { showSpawnDialog = false },
            onSpawn = { numbers, special ->
                addMonsterUnit(numbers, item.id, special)
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
            item = MonsterItem(
                id = 1,
                isBoss = true,
                name = "Хвостожабка",
                currentCard = null,
                isFly = true,
                units = persistentListOf(
                    MonsterUnit.fixture(1),
                )
            ),
            delete = {},
            deleteUnit = { _, _ -> },
            updateUnitLife = { _, _, _ -> },
            switchUnitEffect = { _, _, _ -> },
            addMonsterUnit = { _, _, _ -> },
            changeUnitLevel = { _, _, _ -> }
        )
    }
}
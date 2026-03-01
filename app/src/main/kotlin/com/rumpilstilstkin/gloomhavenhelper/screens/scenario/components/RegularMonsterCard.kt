package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import com.rumpilstilstkin.gloomhavenhelper.screens.models.EffectItem
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterItem
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterUnit
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.CardColors
import com.rumpilstilstkin.gloomhavenhelper.ui.theme.GloomhavenHalperTheme

@Composable
fun RegularMonsterCard(
    item: MonsterItem,
    modifier: Modifier = Modifier,
    delete: (monsterNumber: Int) -> Unit,
    deleteUnit: (unitNumber: Int, monsterId: Int) -> Unit,
    updateUnitLife: (unitNumber: Int, monsterId: Int, life: Int) -> Unit,
    switchUnitEffect: (unitNumber: Int, monsterId: Int, effect: ActionUi) -> Unit,
    addMonsterUnit: (unitNumbers: List<Int>, monsterId: Int, isSpecial: Boolean) -> Unit,
) {
    var showSpawnDialog by remember { mutableStateOf(false) }

    BasicCard(modifier) {
        MonsterCardHeader(
            name = item.name,
            isFly = item.isFly,
            delete = { delete(item.id) },
            onAddUnit = { showSpawnDialog = true }
        )
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )

        MonsterActionCard(
            card = item.currentCard
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Активные враги",
            fontSize = 9.sp,
            letterSpacing = 1.sp,
            color = CardColors.TextMuted,
            modifier = Modifier.padding(vertical = 2.dp),
        )

        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
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
                        }
                    )
                }
            }
        }
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

@Preview
@Composable
private fun RegularMonsterCardPreview() {
    GloomhavenHalperTheme {
        RegularMonsterCard(
            item = MonsterItem(
                id = 1,
                name = "Хвостожабка",
                currentCard = null,
                isFly = true,
                units = listOf(
                    MonsterUnit(
                        number = 2,
                        isSpecial = true,
                        currentLife = 10,
                        maxLife = 10,
                        stats = listOf(
                            EffectItem.Action(
                                type = ActionUi.MOVE,
                                modifier = "3"
                            ),
                            EffectItem.Action(
                                type = ActionUi.ATTACK,
                                modifier = "4"
                            ),
                            EffectItem.Action(
                                type = ActionUi.SHIELD,
                                modifier = "2"
                            ),
                        )
                    ),
                    MonsterUnit(
                        number = 3,
                        isSpecial = false,
                        currentLife = 10,
                        maxLife = 10,
                        stats = listOf(
                            EffectItem.Action(
                                type = ActionUi.MOVE,
                                modifier = "3"
                            ),
                            EffectItem.Action(
                                type = ActionUi.ATTACK,
                                modifier = "4"
                            ),
                            EffectItem.Action(
                                type = ActionUi.SHIELD,
                                modifier = "2"
                            ),
                        )
                    ),
                )
            ),
            delete = {},
            deleteUnit = { _, _ -> },
            updateUnitLife = { _, _, _ -> },
            switchUnitEffect = { _, _, _ -> },
            addMonsterUnit = { _, _, _ -> }
        )
    }
}
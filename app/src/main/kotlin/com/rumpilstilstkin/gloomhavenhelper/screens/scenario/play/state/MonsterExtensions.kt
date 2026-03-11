package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.state

import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterItem
import com.rumpilstilstkin.gloomhavenhelper.screens.models.MonsterUnit
import kotlinx.collections.immutable.toImmutableList

fun List<MonsterItem>.updateMonster(
    monsterId: Int,
    transform: (MonsterItem) -> MonsterItem
): List<MonsterItem> = map { monster ->
    if (monster.id == monsterId) transform(monster) else monster
}

fun MonsterItem.updateUnit(
    unitNumber: Int,
    transform: (MonsterUnit) -> MonsterUnit
): MonsterItem = copy(
    units = units.map { unit ->
        if (unit.number == unitNumber) transform(unit) else unit
    }.toImmutableList()
)

fun MonsterItem.filterUnits(
    predicate: (MonsterUnit) -> Boolean
): MonsterItem = copy(
    units = units.filter(predicate).toImmutableList()
)

fun MonsterItem.addUnits(
    newUnits: List<MonsterUnit>
): MonsterItem = copy(
    units = (units + newUnits).toImmutableList()
)

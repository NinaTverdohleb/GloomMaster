package com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario

import kotlinx.collections.immutable.toImmutableList

fun List<MonsterItem>.updateMonster(
    monsterSlug: String,
    transform: (MonsterItem) -> MonsterItem
): List<MonsterItem> = map { monster ->
    if (monster.slug == monsterSlug) transform(monster) else monster
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

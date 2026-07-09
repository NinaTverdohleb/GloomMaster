package com.rumpilstilstkin.gloommaster.domain.entity.scenario

fun Map<String, MonsterItem>.updateMonster(
    monsterSlug: String,
    transform: MonsterItem.() -> MonsterItem,
): Map<String, MonsterItem> =
    this[monsterSlug]?.transform()?.let {
        this + (monsterSlug to it)
    } ?: this

fun MonsterItem.addUnits(newUnits: Map<Int, MonsterUnit>): MonsterItem =
    copy(
        units = (units + newUnits),
    )

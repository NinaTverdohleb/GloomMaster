package com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario

import kotlinx.collections.immutable.toImmutableList

fun List<MonsterItem>.updateMonster(
    monsterSlug: String,
    transform: (MonsterItem) -> MonsterItem,
): List<MonsterItem> =
    map { monster ->
        if (monster.slug == monsterSlug) transform(monster) else monster
    }

fun MonsterItem.addUnits(newUnits: List<MonsterUnit>): MonsterItem =
    copy(
        units = (units + newUnits).toImmutableList(),
    )

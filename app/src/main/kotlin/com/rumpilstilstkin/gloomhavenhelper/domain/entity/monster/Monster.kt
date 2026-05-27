package com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Monster(
    val id: Int,
    val name: String,
    val life: Int,
    val stats: List<MonsterAction>,
    val eliteLife: Int,
    val eliteStats: List<MonsterAction>,
    val deckName: String,
    val cards: List<MonsterCard>,
    val isBoss: Boolean,
    val immunity: List<MonsterStatType>,
    val isFly: Boolean,
    val level: Int,
    val lifeMultiple: Boolean,
    // Canonical [name] is the identity: it is matched by scenario monster lists and persisted in
    // the active-scenario game state, so it must never change with language. [displayName] is the
    // display-only localized text, defaults to [name], and is @Transient so it is never serialized.
    @Transient val displayName: String = name,
)


data class MonsterStats(
    val monsterId: Int,
    val level: Int,
    val isElite: Boolean,
    val life: Int,
    val stats: List<MonsterAction>,
)
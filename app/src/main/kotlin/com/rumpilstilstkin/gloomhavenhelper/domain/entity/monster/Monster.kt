package com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Monster(
    val id: Int,
    val key: String,
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
    // [key] is the stable catalog identity: it is matched by scenario monster lists and persisted
    // in the active-scenario game state, so it is language-independent. [displayName] is the
    // display-only localized text, defaults to [key], and is @Transient so it is never serialized.
    @Transient val displayName: String = key,
)


data class MonsterStats(
    val monsterId: Int,
    val level: Int,
    val isElite: Boolean,
    val life: Int,
    val stats: List<MonsterAction>,
)
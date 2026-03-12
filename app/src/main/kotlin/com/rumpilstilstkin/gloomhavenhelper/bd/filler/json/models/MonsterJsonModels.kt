package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterAction
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import kotlinx.serialization.Serializable

@Serializable
data class MonsterJson(
    val name: String,
    val deckName: String,
    val isBoss: Boolean,
    val fly: Boolean = false,
    val lifeMultiple: Boolean = false,
    val immunity: List<MonsterStatType> = emptyList(),
    val pack: String
)

@Serializable
data class MonsterStatsJson(
    val monsterName: String,
    val stats: List<MonsterLevelStatsJson>
)

@Serializable
data class MonsterLevelStatsJson(
    val level: Int,
    val isElite: Boolean,
    val life: Int,
    val stats: List<MonsterAction>
)

@Serializable
data class AbilityDeckJson(
    val deckName: String,
    val cards: List<AbilityCardJson>
)

@Serializable
data class AbilityCardJson(
    val initiative: Int,
    val imageName: String? = null,
    val actions: List<MonsterAction>? = null,
    val needsShuffle: Boolean = false
)

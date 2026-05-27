package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterAction
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import kotlinx.serialization.Serializable

@Serializable
data class MonsterJson(
    // Stable catalog key. Used as the translation-store key for this monster's name; the (Russian)
    // [name] remains the logic/save identity. Not persisted to the DB.
    val key: String,
    val name: String,
    val deckName: String,
    val isBoss: Boolean,
    val fly: Boolean = false,
    val lifeMultiple: Boolean = false,
    val immunity: List<MonsterStatType> = emptyList(),
    val pack: String
){
    fun toEntity() = MonsterBd(
        name = name,
        deckName = deckName,
        isBoss = isBoss,
        fly = fly,
        lifeMultiple = lifeMultiple,
        immunity = immunity,
        pack = pack
    )
}

@Serializable
data class MonsterStatsJson(
    val monsterName: String,
    val stats: List<MonsterLevelStatsJson>
)

/**
 * Catalog of the deduplicated ability/special text embedded in monster stats. [text] is the
 * canonical Russian content (matched verbatim against the stats data to bridge to [key]); the
 * per-locale dictionaries are keyed by [key]. Not persisted to the DB.
 */
@Serializable
data class MonsterTextJson(
    val key: String,
    val text: String,
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

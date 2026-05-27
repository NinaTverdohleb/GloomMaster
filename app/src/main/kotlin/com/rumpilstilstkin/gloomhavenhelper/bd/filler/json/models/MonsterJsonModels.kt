package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterAction
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import kotlinx.serialization.Serializable

@Serializable
data class MonsterJson(
    // Stable catalog key — the logic/save identity (scenario monster lists, active-play game
    // state) and the translation-store key. The display name lives only in the per-locale
    // dictionaries, resolved by this key.
    val key: String,
    val deckName: String,
    val isBoss: Boolean,
    val fly: Boolean = false,
    val lifeMultiple: Boolean = false,
    val immunity: List<MonsterStatType> = emptyList(),
    val pack: String
){
    fun toEntity() = MonsterBd(
        key = key,
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
    val monsterKey: String,
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

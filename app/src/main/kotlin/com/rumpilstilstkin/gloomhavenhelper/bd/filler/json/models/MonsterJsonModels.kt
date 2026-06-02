package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterTextStatsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.MonsterTranslationsBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterAction
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import kotlinx.serialization.Serializable

@Serializable
data class MonsterJson(
    val slug: String,
    val deckName: String,
    val isBoss: Boolean,
    val fly: Boolean = false,
    val lifeMultiple: Boolean = false,
    val immunity: List<MonsterStatType> = emptyList(),
    val pack: String,
) {
    fun toEntity() =
        MonsterBd(
            slug = slug,
            deckName = deckName,
            isBoss = isBoss,
            fly = fly,
            lifeMultiple = lifeMultiple,
            immunity = immunity,
            pack = pack,
        )
}

@Serializable
data class MonsterTranslationJson(
    val slug: String,
    val name: String,
) {
    fun toEntity(locale: String) =
        MonsterTranslationsBd(
            slug = slug,
            locale = locale,
            name = name,
        )
}

@Serializable
data class MonsterStatsJson(
    val monsterSlug: String,
    val stats: List<MonsterLevelStatsJson>,
)

@Serializable
data class MonsterLevelStatsJson(
    val level: Int,
    val isElite: Boolean,
    val life: Int,
    val stats: List<MonsterAction>,
)

@Serializable
data class MonsterTranslationStatsJson(
    val monsterSlug: String,
    val stats: List<MonsterLevelTranslationStatsJson>,
)

@Serializable
data class MonsterLevelTranslationStatsJson(
    val level: Int,
    val isElite: Boolean,
    val textStats: List<String>,
)

package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.AchievementBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.AchievementTranslateBd
import kotlinx.serialization.Serializable

@Serializable
data class AchievementJson(
    val slug: String,
    val pack: String,
    val isGlobal: Boolean,
    val maxRang: Int = 1
) {
    fun toEntity() = AchievementBd(
        slug = slug,
        pack = pack,
        isGlobal = isGlobal,
        maxRang = maxRang
    )
}

@Serializable
data class AchievementTranslationJson(
    val slug: String,
    val name: String
) {
    fun toEntity(locale: String) = AchievementTranslateBd(
        slug = slug,
        locale = locale,
        name = name
    )
}

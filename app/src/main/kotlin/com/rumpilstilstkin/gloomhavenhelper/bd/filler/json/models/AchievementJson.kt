package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.AchievementBd
import kotlinx.serialization.Serializable

@Serializable
data class AchievementJson(
    // Stable catalog key. Used as the translation-store key for this achievement; the (Russian)
    // [name] remains the logic/save identity. Not persisted to the DB.
    val key: String,
    val name: String,
    val pack: String,
    val isGlobal: Boolean,
    val maxRang: Int = 1
) {
    fun toEntity() = AchievementBd(
        name = name,
        pack = pack,
        isGlobal = isGlobal,
        maxRang = maxRang
    )
}

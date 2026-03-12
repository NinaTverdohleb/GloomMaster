package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.AchievementBd
import kotlinx.serialization.Serializable

@Serializable
data class AchievementJson(
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

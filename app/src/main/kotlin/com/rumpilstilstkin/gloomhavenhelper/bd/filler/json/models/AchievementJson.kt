package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.AchievementBd
import kotlinx.serialization.Serializable

@Serializable
data class AchievementJson(
    // Stable catalog key — the logic/save identity and the translation-store key. The display
    // name lives only in the per-locale dictionaries, resolved by this key.
    val key: String,
    val pack: String,
    val isGlobal: Boolean,
    val maxRang: Int = 1
) {
    fun toEntity() = AchievementBd(
        key = key,
        pack = pack,
        isGlobal = isGlobal,
        maxRang = maxRang
    )
}

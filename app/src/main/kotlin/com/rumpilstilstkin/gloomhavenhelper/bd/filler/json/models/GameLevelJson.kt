package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GameLevelInfoBd
import kotlinx.serialization.Serializable

@Serializable
data class GameLevelJson(
    val level: Int,
    val monsterLevel: Int,
    val goldCount: Int,
    val trapDamage: Int,
    val experience: Int
) {
    fun toEntity() = GameLevelInfoBd(
        level = level,
        monsterLevel = monsterLevel,
        goldCount = goldCount,
        trapDamage = trapDamage,
        experience = experience
    )
}

package com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster

import kotlinx.serialization.Serializable

@Serializable
data class MonsterStat(
    val type: MonsterStatType,
    val value: Int,
)

@Serializable
enum class MonsterStatType {
    LIFE,
    ATTACK,
    MOVE,
    RANGE,
    SHIELD,
    RETALIATE,
    TARGET,
    POISON,
    WOUND,
    MUDDLE,
    STUN,
    IMMOBILIZE,
    DISARM,
    CURSE,
    STRENGTHEN,
    INVISIBLE,
}

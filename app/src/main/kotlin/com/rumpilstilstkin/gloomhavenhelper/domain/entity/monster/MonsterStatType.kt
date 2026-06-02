package com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster

import kotlinx.serialization.Serializable

@Serializable
enum class MonsterStatType {
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
    HEAL,
    PUSH,
    BLESS,
    PULL,
    PIERCE,
    REGENERATE,
    ;

    companion object {
        val mainEffectsPack =
            setOf(
                POISON,
                WOUND,
                IMMOBILIZE,
                DISARM,
                STUN,
                MUDDLE,
                STRENGTHEN,
                INVISIBLE,
            )
        val fcEffectsPack =
            setOf(
                REGENERATE,
            )
    }
}

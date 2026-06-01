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
    REGENERATE;

    companion object {
        val mainEffectsPack = setOf(
            POISON,
            MonsterStatType.WOUND,
            MonsterStatType.IMMOBILIZE,
            MonsterStatType.DISARM,
            MonsterStatType.STUN,
            MonsterStatType.MUDDLE,
            MonsterStatType.STRENGTHEN,
            MonsterStatType.INVISIBLE
        )
        val fcEffectsPack = setOf(
            MonsterStatType.REGENERATE
        )
    }
}

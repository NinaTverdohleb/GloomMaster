package com.rumpilstilstkin.gloomhavenhelper.data.mappers

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.GameLevelInfoBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.LevelInfo

fun GameLevelInfoBd.toDomain() = LevelInfo(
    level = level,
    monsterLevel = monsterLevel,
    goldCount = goldCount,
    trapDamage = trapDamage,
    experience = experience
)
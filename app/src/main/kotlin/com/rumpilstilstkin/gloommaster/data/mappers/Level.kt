package com.rumpilstilstkin.gloommaster.data.mappers

import com.rumpilstilstkin.gloommaster.bd.entity.GameLevelInfoBd
import com.rumpilstilstkin.gloommaster.domain.entity.LevelInfo

fun GameLevelInfoBd.toDomain() =
    LevelInfo(
        level = level,
        monsterLevel = monsterLevel,
        goldCount = goldCount,
        trapDamage = trapDamage,
        experience = experience,
    )

package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GameLevelInfoBd(
    @PrimaryKey val level: Int,
    val monsterLevel: Int,
    val goldCount: Int,
    val trapDamage: Int,
    val experience: Int
)
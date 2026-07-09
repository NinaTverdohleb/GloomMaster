package com.rumpilstilstkin.gloommaster.bd.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GameLevelInfoBd(
    @PrimaryKey val level: Int,
    val monsterLevel: Int,
    val goldCount: Int,
    val trapDamage: Int,
    val experience: Int,
) {
    companion object {
        fun fixture(
            level: Int = 1,
            monsterLevel: Int = 1,
            goldCount: Int = 30,
            trapDamage: Int = 2,
            experience: Int = 4,
        ): GameLevelInfoBd =
            GameLevelInfoBd(
                level = level,
                monsterLevel = monsterLevel,
                goldCount = goldCount,
                trapDamage = trapDamage,
                experience = experience,
            )
    }
}

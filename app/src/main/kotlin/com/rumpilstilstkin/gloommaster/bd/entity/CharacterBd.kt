package com.rumpilstilstkin.gloommaster.bd.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterBd(
    @PrimaryKey(autoGenerate = true) val characterId: Int = 0,
    val name: String,
    val level: Int,
    val experience: Int,
    val goldCount: Int,
    val characterType: String,
    val teamId: Int?,
    val isAlive: Boolean = true,
    val notes: String = "",
    val checkMarkCount: Int = 0,
    val additionalContOfPerks: Int = 0,
)

package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterBd(
    @PrimaryKey(autoGenerate = true) val characterId: Int = 0,
    val name: String,
    val level: Int,
    val classId: Int,
    val teamId: Int?,
    val isAlive: Boolean = true,
)
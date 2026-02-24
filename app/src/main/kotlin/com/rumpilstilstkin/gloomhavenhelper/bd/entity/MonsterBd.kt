package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MonsterBd(
    @PrimaryKey(autoGenerate = true) val monsterId: Int = 0,
    val name: String,
)

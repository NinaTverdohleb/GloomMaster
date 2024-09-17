package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TeamBd (
    @PrimaryKey(autoGenerate = true) val teamId: Int = 0,
    val name: String,
    val teamAchievement: String = "",
    val globalAchievement: String = "",
    val reputation: Int = 0,
    val prosperity: Int = 0,

    )
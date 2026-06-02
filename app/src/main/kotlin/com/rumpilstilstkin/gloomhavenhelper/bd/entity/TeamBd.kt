package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.Achievement

@Entity
data class TeamBd(
    @PrimaryKey(autoGenerate = true) val teamId: Int = 0,
    val name: String,
    val achievements: List<Achievement> = emptyList(),
    val reputation: Int = 0,
    val prosperity: Int = 0,
    val churchValue: Int = 100,
    val packs: List<String>,
    val difficultyLevel: Int = 0,
)

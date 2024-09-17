package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ScenarioBd(
    @PrimaryKey val scenarioNumber: Int,
    val name: String,
    val newScenarios: String = "",
    val teamAchievement: String= "",
    val globalAchievement: String= "",
    val requirements: String = "",
)

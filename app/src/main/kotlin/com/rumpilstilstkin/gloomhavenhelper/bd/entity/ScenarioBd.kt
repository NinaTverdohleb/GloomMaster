package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType

@Entity
data class ScenarioBd(
    @PrimaryKey val scenarioNumber: Int,
    val newScenarios: String = "",
    // Achievement catalog keys (a "(-)" prefix marks removal); monsters holds monster catalog keys.
    val teamAchievement: String= "",
    val globalAchievement: String= "",
    val requirements: String = "",
    val monsters: List<String> = emptyList(),
    val pack: String,
)

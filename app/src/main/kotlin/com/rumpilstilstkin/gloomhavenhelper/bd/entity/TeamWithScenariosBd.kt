package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TeamWithScenariosBd(
    @Embedded
    val team: TeamBd,
    @Relation(parentColumn = "teamId", entityColumn = "teamId")
    val scenarios: List<TeamScenarioBd>
)
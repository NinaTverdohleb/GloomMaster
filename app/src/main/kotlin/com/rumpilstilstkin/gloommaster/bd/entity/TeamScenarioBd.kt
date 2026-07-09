package com.rumpilstilstkin.gloommaster.bd.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = TeamBd::class,
            parentColumns = arrayOf("teamId"),
            childColumns = arrayOf("teamId"),
            onDelete = CASCADE,
        ),
        ForeignKey(
            entity = ScenarioBd::class,
            parentColumns = arrayOf("scenarioNumber"),
            childColumns = arrayOf("scenarioNumber"),
            onDelete = CASCADE,
        ),
    ],
    indices = [
        Index("teamId"),
        Index("scenarioNumber"),
    ],
)
data class TeamScenarioBd(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val teamId: Int,
    val scenarioNumber: Int,
    val completed: Boolean = false,
)

data class TeamScenarioBdDetailsBd(
    @Embedded val teamScenario: TeamScenarioBd,
    @Relation(
        parentColumn = "scenarioNumber",
        entityColumn = "scenarioNumber",
    )
    val scenario: ScenarioBd,
)

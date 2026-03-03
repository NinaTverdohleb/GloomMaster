package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
        ForeignKey(
            entity = TeamBd::class,
            parentColumns = arrayOf("teamId"),
            childColumns = arrayOf("teamId"),
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = ScenarioBd::class,
            parentColumns = arrayOf("scenarioNumber"),
            childColumns = arrayOf("scenarioNumber"),
            onDelete = CASCADE
        )
    ],
    indices = [
        Index("teamId"),
        Index("scenarioNumber"),
    ]
)
data class TeamScenarioBd(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val teamId: Int,
    val scenarioNumber: Int,
    val scenarioName: String,
    val scenarioRequirements: String,
    val completed: Boolean = false,
    val location: String = ""
)
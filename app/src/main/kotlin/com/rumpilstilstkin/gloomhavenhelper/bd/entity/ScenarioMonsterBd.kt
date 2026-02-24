package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ScenarioBd::class,
            parentColumns = arrayOf("scenarioNumber"),
            childColumns = arrayOf("scenarioNumber"),
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = MonsterBd::class,
            parentColumns = arrayOf("monsterId"),
            childColumns = arrayOf("monsterId"),
            onDelete = CASCADE
        )
    ],
    indices = [
        Index("scenarioNumber"),
        Index("monsterId")
    ]
)
data class ScenarioMonsterBd(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val scenarioNumber: Int,
    val monsterId: Int,
)

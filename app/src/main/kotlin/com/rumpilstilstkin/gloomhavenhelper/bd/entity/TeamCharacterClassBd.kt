package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = TeamBd::class,
            parentColumns = arrayOf("teamId"),
            childColumns = arrayOf("teamId"),
            onDelete = CASCADE,
        ),
    ],
    indices = [
        Index("teamId"),
    ],
)
data class TeamCharacterClassBd(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val teamId: Int,
    val characterType: String,
)

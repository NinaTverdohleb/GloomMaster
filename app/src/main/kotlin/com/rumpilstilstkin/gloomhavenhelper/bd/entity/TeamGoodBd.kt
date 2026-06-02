package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE

@Entity(
    primaryKeys = ["teamId", "goodId"],
    foreignKeys = [
        ForeignKey(
            entity = TeamBd::class,
            parentColumns = arrayOf("teamId"),
            childColumns = arrayOf("teamId"),
            onDelete = CASCADE,
        ),
    ],
)
data class TeamGoodBd(
    val teamId: Int,
    val goodId: Int,
)

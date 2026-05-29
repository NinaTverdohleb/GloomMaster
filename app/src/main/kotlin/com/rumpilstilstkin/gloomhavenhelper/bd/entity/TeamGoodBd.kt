package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.Relation

@Entity(
    primaryKeys = ["teamId", "goodNumber"],
    foreignKeys = [
        ForeignKey(
            entity = TeamBd::class,
            parentColumns = arrayOf("teamId"),
            childColumns = arrayOf("teamId"),
            onDelete = CASCADE
        )
    ],
)
data class TeamGoodBd(
    val teamId: Int,
    val goodNumber: Int,
)

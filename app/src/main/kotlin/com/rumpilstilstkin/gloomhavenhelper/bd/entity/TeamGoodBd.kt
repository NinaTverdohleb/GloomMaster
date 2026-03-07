package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index

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
    indices = [
        Index("teamId"),
        Index("goodNumber"),
    ]
)
data class TeamGoodBd(
    val teamId: Int,
    val goodNumber: Int,
)

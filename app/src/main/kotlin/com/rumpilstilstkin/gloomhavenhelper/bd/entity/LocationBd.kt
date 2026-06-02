package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class LocationBd(
    @PrimaryKey val slug: String,
)

@Entity(
    primaryKeys = ["slug", "locale"],
    foreignKeys = [
        ForeignKey(
            entity = LocationBd::class,
            parentColumns = ["slug"],
            childColumns = ["slug"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class LocationTranslateBd(
    val slug: String,
    val locale: String,
    val name: String,
)

package com.rumpilstilstkin.gloommaster.bd.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class AchievementBd(
    @PrimaryKey val slug: String,
    val pack: String,
    val maxRang: Int = 1,
    @ColumnInfo(name = "isGlobal") val isGlobal: Boolean,
)

@Entity(
    primaryKeys = ["slug", "locale"],
    foreignKeys = [
        ForeignKey(
            entity = AchievementBd::class,
            parentColumns = ["slug"],
            childColumns = ["slug"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class AchievementTranslateBd(
    val slug: String,
    val locale: String,
    val name: String,
)

data class AchievementWithTranslation(
    val slug: String,
    val pack: String,
    val maxRang: Int,
    @ColumnInfo(name = "isGlobal") val isGlobal: Boolean,
    val name: String,
)

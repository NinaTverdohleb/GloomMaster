package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class GoodBd(
    @PrimaryKey val goodNumber: Int,
    val type: String,
    val cost: Int,
    val image: String,
    val pack: String,
    @ColumnInfo(name = "is_drawing") val isDrawing: Boolean = false,
)

@Entity(
    primaryKeys = ["goodNumber", "locale"],
    foreignKeys = [
        ForeignKey(
            entity = GoodBd::class,
            parentColumns = ["goodNumber"],
            childColumns = ["goodNumber"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GoodTranslationsBd(
    val goodNumber: Int,
    val locale: String,
    val name: String,
)

data class GoodWithTranslation(
    @Embedded
    val good: GoodBd,

    @ColumnInfo(name = "translated_name")
    val name: String
)
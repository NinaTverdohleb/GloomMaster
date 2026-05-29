package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class GoodBd(
    @PrimaryKey(autoGenerate = true) val goodId: Int = 0,
    val displayNumber: Int,
    val type: String,
    val cost: Int,
    val image: String,
    val pack: String,
    @ColumnInfo(name = "is_drawing") val isDrawing: Boolean = false,
)

@Entity(
    primaryKeys = ["displayNumber", "locale"],
)
data class GoodTranslationsBd(
    val displayNumber: Int,
    val locale: String,
    val name: String,
)

data class GoodWithTranslation(
    @Embedded
    val good: GoodBd,

    @ColumnInfo(name = "translated_name")
    val name: String
)
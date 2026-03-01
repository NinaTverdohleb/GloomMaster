package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GoodBd(
    @PrimaryKey(autoGenerate = true) val goodId: Int = 0,
    @ColumnInfo(name = "number") val number: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "cost") val cost: Int,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "is_drawing") val isDrawing: Boolean = false
)
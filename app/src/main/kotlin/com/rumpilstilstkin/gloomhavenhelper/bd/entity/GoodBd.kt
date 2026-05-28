package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GoodBd(
    @PrimaryKey(autoGenerate = true) val goodId: Int = 0,
    // Stable catalog key — the translation-store key. Display name resolved from the translation store.
    @ColumnInfo(name = "key") val key: String,
    @ColumnInfo(name = "number") val number: Int,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "cost") val cost: Int,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "pack") val pack: String,
    @ColumnInfo(name = "is_drawing") val isDrawing: Boolean = false,
)
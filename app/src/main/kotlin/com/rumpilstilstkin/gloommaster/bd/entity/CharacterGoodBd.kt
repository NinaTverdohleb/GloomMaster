package com.rumpilstilstkin.gloommaster.bd.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = CharacterBd::class,
            parentColumns = arrayOf("characterId"),
            childColumns = arrayOf("characterId"),
            onDelete = CASCADE,
        ),
        ForeignKey(
            entity = GoodBd::class,
            parentColumns = arrayOf("goodId"),
            childColumns = arrayOf("goodId"),
            onDelete = CASCADE,
        ),
    ],
    indices = [
        Index("characterId"),
        Index("goodId"),
    ],
)
data class CharacterGoodBd(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val characterId: Int,
    val goodId: Int,
)

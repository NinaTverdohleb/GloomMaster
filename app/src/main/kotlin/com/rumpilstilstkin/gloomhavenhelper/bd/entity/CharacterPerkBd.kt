package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Embedded
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
    ],
    indices = [
        Index("characterId"),
    ],
)
data class CharacterPerkBd(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val characterId: Int,
    val perkId: Int,
)

data class CharacterPerkWithNameBd(
    @Embedded
    val perk: CharacterPerkBd,
    val text: String,
)

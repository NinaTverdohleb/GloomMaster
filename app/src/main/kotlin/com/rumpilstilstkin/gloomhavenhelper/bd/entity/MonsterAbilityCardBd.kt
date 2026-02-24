package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.CardAction

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = MonsterBd::class,
            parentColumns = arrayOf("monsterId"),
            childColumns = arrayOf("monsterId"),
            onDelete = CASCADE
        )
    ],
    indices = [
        Index("monsterId")
    ]
)
data class MonsterAbilityCardBd(
    @PrimaryKey(autoGenerate = true) val cardId: Int = 0,
    val monsterId: Int,
    val initiative: Int,
    val actions: List<CardAction>,
    val needsShuffle: Boolean = false,
)

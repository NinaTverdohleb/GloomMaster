package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterAction
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCardAction

@Entity(
    primaryKeys = ["deckName", "cardId"],
)
data class MonsterAbilityCardBd(
    val deckName: String,
    val needsShuffle: Boolean = false,
    val initiative: Int,
    val cardId: Int,
)

@Entity(
    primaryKeys = ["deckName", "locale", "cardId"],
    foreignKeys = [
        ForeignKey(
            entity = MonsterAbilityCardBd::class,
            parentColumns = ["deckName", "cardId"],
            childColumns = ["deckName", "cardId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("deckName", "cardId")],
)
data class MonsterAbilityCardTranslationBd(
    val deckName: String,
    val locale: String,
    val cardId: Int,
    val actions: List<MonsterCardAction>,
)

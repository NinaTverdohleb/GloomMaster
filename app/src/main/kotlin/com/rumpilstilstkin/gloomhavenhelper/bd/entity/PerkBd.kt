package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PerkBd(
    val count: Int,
    @PrimaryKey val characterType: String,
)

@Entity(
    primaryKeys = ["perkId", "locale", "characterType"],
)
data class PerkTranslationBd(
    val perkId: Int,
    val locale: String,
    val text: String,
    val characterType: String,
)
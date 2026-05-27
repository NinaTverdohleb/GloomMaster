package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType

@Entity
data class MonsterBd(
    @PrimaryKey(autoGenerate = true) val monsterId: Int = 0,
    // Stable catalog key — the logic/save identity. Display name resolved from the translation store.
    val key: String,
    val deckName: String,
    val isBoss: Boolean,
    val fly: Boolean = false,
    val lifeMultiple: Boolean = false,
    val immunity: List<MonsterStatType> = emptyList(),
    val pack: String = PackType.MAIN.name,
)

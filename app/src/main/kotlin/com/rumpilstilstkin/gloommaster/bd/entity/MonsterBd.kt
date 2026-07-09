package com.rumpilstilstkin.gloommaster.bd.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.rumpilstilstkin.gloommaster.domain.entity.PackType
import com.rumpilstilstkin.gloommaster.domain.entity.monster.MonsterStatType

@Entity
data class MonsterBd(
    @PrimaryKey val slug: String,
    val deckName: String,
    val isBoss: Boolean,
    val fly: Boolean = false,
    val lifeMultiple: Boolean = false,
    val immunity: List<MonsterStatType> = emptyList(),
    val pack: String = PackType.MAIN.name,
)

@Entity(
    primaryKeys = ["slug", "locale"],
    foreignKeys = [
        ForeignKey(
            entity = MonsterBd::class,
            parentColumns = ["slug"],
            childColumns = ["slug"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class MonsterTranslationsBd(
    val slug: String,
    val locale: String,
    val name: String,
)

data class MonsterWithNameBd(
    @Embedded
    val monster: MonsterBd,
    val name: String,
)

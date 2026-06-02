package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterAction

@Entity(
    primaryKeys = ["monsterSlug", "scenarioLevel", "isElite"],
    foreignKeys = [
        ForeignKey(
            entity = MonsterBd::class,
            parentColumns = arrayOf("slug"),
            childColumns = arrayOf("monsterSlug"),
            onDelete = CASCADE,
        ),
    ],
    indices = [
        Index("monsterSlug"),
    ],
)
data class MonsterStatsBd(
    val monsterSlug: String,
    val scenarioLevel: Int,
    val isElite: Boolean,
    val life: Int,
    val stats: List<MonsterAction>,
)

@Entity(
    primaryKeys = ["monsterSlug", "scenarioLevel", "isElite", "locale"],
    foreignKeys = [
        ForeignKey(
            entity = MonsterBd::class,
            parentColumns = arrayOf("slug"),
            childColumns = arrayOf("monsterSlug"),
            onDelete = CASCADE,
        ),
    ],
    indices = [
        Index("monsterSlug"),
    ],
)
data class MonsterTextStatsBd(
    val locale: String,
    val monsterSlug: String,
    val scenarioLevel: Int,
    val isElite: Boolean,
    val stats: List<MonsterAction>,
)

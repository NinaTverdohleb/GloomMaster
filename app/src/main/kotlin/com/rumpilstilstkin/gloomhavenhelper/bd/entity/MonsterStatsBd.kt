package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStat

@Entity(
    primaryKeys = ["monsterId", "scenarioLevel", "isElite"],
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
data class MonsterStatsBd(
    val monsterId: Int,
    val scenarioLevel: Int,
    val isElite: Boolean,
    val stats: List<MonsterStat>,
)

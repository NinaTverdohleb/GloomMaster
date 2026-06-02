package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.AvailableCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMagic
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioGameStateMonsterItem

@Entity
data class ScenarioGameStateBd(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val scenarioNumber: Int?,
    val monsterSlugs: List<String>,
    val round: Int = 0,
    val availableCards: List<AvailableCard>,
    val activeMonsters: List<ScenarioGameStateMonsterItem>,
    val magicChargeMap: List<ScenarioGameStateMagic>,
    val level: Int,
)

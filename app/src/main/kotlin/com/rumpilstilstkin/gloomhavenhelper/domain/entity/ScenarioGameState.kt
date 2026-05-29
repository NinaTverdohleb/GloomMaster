package com.rumpilstilstkin.gloomhavenhelper.domain.entity

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import kotlinx.serialization.Serializable

@Serializable
data class ScenarioGameState(
    val scenarioNumber: Int?,
    val monsterNames: List<String>,
    val round: Int,
    val availableCards: List<Int>,
    val activeMonsters: List<ScenarioGameStateMonsterItem>,
    val magicCharges: List<ScenarioGameStateMagic>
)

@Serializable
data class ScenarioGameStateMagic(
    val name: String,
    val value: Int
)

@Serializable
data class ScenarioGameStateMonsterItem(
    val slug: String,
    val currentCard: Int? = null,
    val units: List<ScenarioGameStateMonsterUnit> = listOf(),
)

@Serializable
data class ScenarioGameStateMonsterUnit(
    val number: Int,
    val currentLife: Int,
    val level: Int,
    val isElite: Boolean,
    val effects: List<MonsterStatType>
)

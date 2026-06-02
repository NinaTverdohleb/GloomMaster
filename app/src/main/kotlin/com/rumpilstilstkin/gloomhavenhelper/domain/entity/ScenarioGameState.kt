package com.rumpilstilstkin.gloomhavenhelper.domain.entity

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import kotlinx.serialization.Serializable

@Serializable
data class ScenarioGameState(
    val level: Int,
    val scenarioNumber: Int?,
    val monsterSlugs: List<String>,
    val round: Int,
    val availableCards: List<AvailableCard>,
    val activeMonsters: List<ScenarioGameStateMonsterItem>,
    val magicCharges: List<ScenarioGameStateMagic>
)

@Serializable
data class ScenarioGameStateMagic(
    val name: String,
    val value: Int
)

@Serializable
data class AvailableCard(
    val deck: String,
    val cardId: Int
)

@Serializable
data class ScenarioGameStateMonsterItem(
    val slug: String,
    val currentCard: AvailableCard? = null,
    val units: List<ScenarioGameStateMonsterUnit> = listOf(),
)

@Serializable
data class ScenarioGameStateMonsterUnit(
    val number: Int,
    val currentLife: Int,
    val level: Int,
    val isElite: Boolean,
    val effects: List<MonsterStatType>,
    val isNew: Boolean = false,
    val maxLife: Int = 0
)

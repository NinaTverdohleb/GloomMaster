package com.rumpilstilstkin.gloomhavenhelper.domain.entity

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterCard
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterStatType
import kotlinx.serialization.Serializable

@Serializable
data class ScenarioBattleInfo(
    val scenarioNumber: Int?,
    val name: String,
    val monsters: List<Monster>,
    val golds: Int,
    val exp: Int,
    val trapDamage: Int,
    val gamersCount: Int,
    val monsterLevel: Int,
    val round: Int = 0,
    val availableCards: List<MonsterCard> = monsters.flatMap { it.cards }.distinct(),
    val activeMonsters: List<ScenarioGameStateMonsterItem> = emptyList(),
    val magicCharges: Map<String, Int> = mapOf(
        Magic.FIRE.name to 0,
        Magic.FROST.name to 0,
        Magic.AIR.name to 0,
        Magic.EARTH.name to 0,
        Magic.SUN.name to 0,
        Magic.MOON.name to 0,
    ),
    val availableEffects: Set<MonsterStatType>
)

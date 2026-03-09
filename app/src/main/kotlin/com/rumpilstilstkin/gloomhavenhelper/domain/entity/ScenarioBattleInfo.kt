package com.rumpilstilstkin.gloomhavenhelper.domain.entity

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.Monster

data class ScenarioBattleInfo(
    val number: Int,
    val name: String,
    val monsters: List<Monster>,
    val golds: Int,
    val exp: Int,
    val trapDamage: Int,
    val gamersCount: Int,
    val monsterLevel: Int,
)

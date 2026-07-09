package com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters.level

import androidx.compose.runtime.Immutable

@Immutable
data class MonsterLevelDialogInput(
    val unitLevel: Int,
    val unitNumber: Int,
    val monsterName: String,
)

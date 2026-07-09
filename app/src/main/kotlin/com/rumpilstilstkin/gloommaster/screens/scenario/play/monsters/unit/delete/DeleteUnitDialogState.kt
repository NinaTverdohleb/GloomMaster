package com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters.unit.delete

import androidx.compose.runtime.Immutable

@Immutable
data class DeleteUnitDialogInput(
    val monsterSlug: String,
    val monsterName: String,
    val unitNumber: Int,
)

package com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters.delete

import androidx.compose.runtime.Immutable

@Immutable
data class DeleteMonsterDialogInput(
    val monsterSlug: String,
    val monsterName: String,
)

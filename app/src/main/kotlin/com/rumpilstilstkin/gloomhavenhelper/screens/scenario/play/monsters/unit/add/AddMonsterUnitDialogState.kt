package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.monsters.unit.add

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class AddMonsterUnitDialogInput(
    val monsterSlug: String,
    val monsterName: String,
    val availableIds: ImmutableList<Int>,
)

data class AddMonsterUnitDialogState(
    val monsterName: String,
    val availableIds: ImmutableList<Int>,
    val selectedTier: UnitTier = UnitTier.Normal,
    val selectedIds: ImmutableList<Int> = persistentListOf(),
)

sealed interface AddMonsterUnitDialogAction {
    data class SelectTier(
        val tier: UnitTier,
    ) : AddMonsterUnitDialogAction

    data class ToggleId(
        val number: Int,
    ) : AddMonsterUnitDialogAction

    data object Spawn : AddMonsterUnitDialogAction
}

data class AddMonsterUnitDialogResult(
    val monsterSlug: String,
    val numbers: List<Int>,
    val isElite: Boolean,
)

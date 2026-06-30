package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.monsters

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.scenario.MonsterItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MonsterListDialogInput(
    val monsters: List<MonsterItem>,
)

data class MonsterListDialogState(
    val monsters: ImmutableList<MonsterItem> = persistentListOf(),
    val selectedIds: ImmutableList<String> = persistentListOf(),
)

sealed interface MonsterListDialogAction {
    data class ToggleMonster(
        val slug: String,
    ) : MonsterListDialogAction

    data object SelectMonsters : MonsterListDialogAction

    data object AddNewMonsters : MonsterListDialogAction
}

sealed interface MonsterListDialogResult {
    data class Selected(
        val slugs: List<String>,
    ) : MonsterListDialogResult

    data object AddNewMonsters : MonsterListDialogResult
}

package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.monsters

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterName
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MonsterListDialogInput(
    val monsters: List<MonsterName>,
)

data class MonsterListDialogState(
    val monsters: ImmutableList<MonsterName> = persistentListOf(),
    val selectedIds: ImmutableList<String> = persistentListOf(),
)

sealed interface MonsterListDialogAction {
    data class ToggleMonster(
        val slug: String,
    ) : MonsterListDialogAction

    data object SelectMonsters : MonsterListDialogAction
}

sealed interface MonsterListDialogResult {
    data class Selected(
        val slugs: List<String>,
    ) : MonsterListDialogResult
}

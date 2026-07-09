package com.rumpilstilstkin.gloommaster.screens.scenario.play.monsters.add

import com.rumpilstilstkin.gloommaster.domain.entity.monster.MonsterName
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf

data class AddScenarioMonstersDialogState(
    val monsters: ImmutableList<MonsterName> = persistentListOf(),
    val selectedSlugs: ImmutableSet<String> = persistentSetOf(),
    val searchText: String = "",
)

sealed interface AddScenarioMonstersDialogAction {
    data class ChangeSearchText(
        val text: String,
    ) : AddScenarioMonstersDialogAction

    data class ToggleMonster(
        val slug: String,
    ) : AddScenarioMonstersDialogAction

    data object AddMonsters : AddScenarioMonstersDialogAction
}

data class AddScenarioMonstersDialogResult(
    val slugs: List<String>,
)

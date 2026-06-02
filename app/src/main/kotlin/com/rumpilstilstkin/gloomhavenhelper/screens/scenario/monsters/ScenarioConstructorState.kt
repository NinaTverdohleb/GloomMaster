package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.monsters

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap

sealed interface ScenarioConstructorStateUi {
    data object Loading : ScenarioConstructorStateUi

    @Immutable
    data class Content(
        val availableMonsters: ImmutableList<MonsterNameUi>,
        val selectedMonsters: ImmutableList<MonsterNameUi>,
    ) : ScenarioConstructorStateUi
}

data class ScenarioConstructorStateLogic(
    val allMonsters: Map<String, String> = emptyMap(),
    val selectedMonsters: Map<String, String> = emptyMap(),
)

data class MonsterNameUi(
    val slug: String,
    val name: String
)

sealed interface ScenarioConstructorAction {
    data object Back : ScenarioConstructorAction
    data class ToggleMonster(val monster: MonsterNameUi) : ScenarioConstructorAction
    data object AddMonsters : ScenarioConstructorAction
}

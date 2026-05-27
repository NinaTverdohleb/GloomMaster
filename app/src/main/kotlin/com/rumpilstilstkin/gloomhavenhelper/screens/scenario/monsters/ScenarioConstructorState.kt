package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.monsters

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster.MonsterChoice
import kotlinx.collections.immutable.ImmutableList

sealed interface ScenarioConstructorStateUi {
    data object Loading : ScenarioConstructorStateUi

    @Immutable
    data class Content(
        val availableMonsters: ImmutableList<MonsterChoice>,
        val selectedMonsters: ImmutableList<MonsterChoice>,
    ) : ScenarioConstructorStateUi
}

data class ScenarioConstructorStateLogic(
    // [allMonsters] carries canonical name + localized display name; [selectedMonsters] holds the
    // canonical names (the selection/persistence identity), so language switching never alters it.
    val allMonsters: List<MonsterChoice> = emptyList(),
    val selectedMonsters: Set<String> = emptySet(),
)

sealed interface ScenarioConstructorAction {
    data object Back : ScenarioConstructorAction
    data class ToggleMonster(val monster: String) : ScenarioConstructorAction
    data object AddMonsters : ScenarioConstructorAction
}

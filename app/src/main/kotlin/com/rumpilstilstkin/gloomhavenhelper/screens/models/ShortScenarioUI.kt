package com.rumpilstilstkin.gloomhavenhelper.screens.models

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.LogicalCondition
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioShortInfo
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableMap

@Immutable
data class ShortScenarioUI(
    val scenarioNumber: Int,
    val scenarioName: String,
    val scenarioRequirements: LogicalCondition,
    val location: String,
    val completed: Boolean,
    val requirementAchievementNames: ImmutableMap<String, String> = persistentMapOf(),
) {
    companion object {
        fun fixture(
            number: Int = 1
        ) = ShortScenarioUI(
            scenarioNumber = number,
            scenarioName = "Scenario 1",
            scenarioRequirements = LogicalCondition("!Global Achievement"),
            location = "Bad place",
            completed = true
        )
    }
}

fun ScenarioShortInfo.toUi() = ShortScenarioUI(
    scenarioNumber = this.scenarioNumber,
    scenarioName = this.scenarioName,
    scenarioRequirements = this.scenarioRequirements,
    location = this.location,
    completed = this.isCompleted,
    requirementAchievementNames = this.requirementAchievementNames.toImmutableMap(),
)

fun ScenarioInfo.toUi() = ShortScenarioUI(
    scenarioNumber = this.scenarioNumber,
    scenarioName = this.scenarioName,
    scenarioRequirements = this.scenarioRequirements,
    location = this.location,
    completed = this.monsters.isNotEmpty(),
    requirementAchievementNames = this.requirementAchievementNames.toImmutableMap(),
)

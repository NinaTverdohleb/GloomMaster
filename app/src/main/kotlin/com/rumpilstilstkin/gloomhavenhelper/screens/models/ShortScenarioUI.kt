package com.rumpilstilstkin.gloomhavenhelper.screens.models

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioShortInfo

@Immutable
data class ShortScenarioUI(
    val scenarioNumber: Int,
    val scenarioName: String,
    val scenarioRequirements: String,
    val location: String,
    val complated: Boolean,
) {
    companion object {
        fun fixture(
            number: Int = 1
        ) = ShortScenarioUI(
            scenarioNumber = number,
            scenarioName = "Scenario 1",
            scenarioRequirements = "Requirements 1",
            location = "Глубокая жопа",
            complated = true
        )
    }
}

fun ScenarioShortInfo.toUi() = ShortScenarioUI(
    scenarioNumber = this.scenarioNumber,
    scenarioName = this.scenarioName,
    scenarioRequirements = this.scenarioRequirements.toHumanReadable(),
    location = this.location,
    complated = this.isCompleted
)

fun ScenarioInfo.toUi() = ShortScenarioUI(
    scenarioNumber = this.scenarioNumber,
    scenarioName = this.scenarioName,
    scenarioRequirements = this.scenarioRequirements.toHumanReadable(),
    location = this.location,
    complated = this.monsters.isNotEmpty()
)

package com.rumpilstilstkin.gloomhavenhelper.screens.models

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamScenario

@Immutable
data class ShortScenarioUI(
    val scenarioNumber: Int,
    val scenarioName: String,
    val scenarioRequirements: String,
    val location: String,
) {
    companion object {
        fun fixture(
            number: Int = 1
        ) = ShortScenarioUI(
            scenarioNumber = number,
            scenarioName = "Scenario 1",
            scenarioRequirements = "Requirements 1",
            location = "Глубокая жопа"
        )
    }
}

fun TeamScenario.toUi() = ShortScenarioUI(
    scenarioNumber = this.scenarioNumber,
    scenarioName = this.scenarioName,
    scenarioRequirements = this.scenarioRequirements,
    location = this.location
)

fun ScenarioInfo.toUi() = ShortScenarioUI(
    scenarioNumber = this.scenarioNumber,
    scenarioName = this.scenarioName,
    scenarioRequirements = this.scenarioRequirements,
    location = this.location
)

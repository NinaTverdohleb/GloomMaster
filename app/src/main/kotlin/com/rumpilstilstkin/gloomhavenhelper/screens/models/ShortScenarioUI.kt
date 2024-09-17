package com.rumpilstilstkin.gloomhavenhelper.screens.models

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamScenario

data class ShortScenarioUI(
    val scenarioNumber: Int,
    val scenarioName: String,
    val scenarioRequirements: String,
)

fun TeamScenario.toUI() = ShortScenarioUI(
    scenarioNumber = this.scenarioNumber,
    scenarioName = this.scenarioName,
    scenarioRequirements = this.scenarioRequirements,
)

fun ScenarioInfo.toUI() = ShortScenarioUI(
    scenarioNumber = this.scenarioNumber,
    scenarioName = this.scenarioName,
    scenarioRequirements = this.scenarioRequirements,
)

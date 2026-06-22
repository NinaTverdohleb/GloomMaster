package com.rumpilstilstkin.gloomhavenhelper.screens.models

import androidx.compose.runtime.Immutable
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.LogicalCondition
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioInfoWithName

@Immutable
data class ShortScenarioUI(
    val scenarioNumber: Int,
    val scenarioName: String,
    val scenarioRequirements: LogicalCondition,
    val location: String,
    val completed: Boolean,
    val avaliable: Boolean = true
) {
    companion object {
        fun fixture(number: Int = 1) =
            ShortScenarioUI(
                scenarioNumber = number,
                scenarioName = "Scenario 1",
                scenarioRequirements = LogicalCondition("!Global Achievement"),
                location = "Bad place",
                completed = false,
            )
    }
}

fun ScenarioInfoWithName.toUi(avaliable: Boolean = true) =
    ShortScenarioUI(
        scenarioNumber = this.scenarioNumber,
        scenarioName = this.scenarioName,
        scenarioRequirements = this.scenarioRequirements,
        location = this.location,
        completed = this.isCompleted,
        avaliable = avaliable
    )

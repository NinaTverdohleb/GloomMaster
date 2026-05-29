package com.rumpilstilstkin.gloomhavenhelper.domain.entity

data class ScenarioShortInfo(
    val scenarioNumber: Int,
    val scenarioRequirements: LogicalCondition,
    val isCompleted: Boolean,
    val pack: PackType,
    val monsters: List<String>
)

data class ScenarioInfoWithName(
    val scenarioNumber: Int,
    val scenarioName: String,
    val scenarioRequirements: LogicalCondition,
    val newScenario: List<Int>,
    val location: String,
    val pack: PackType,
    val monsters: List<String>,
    val isCompleted: Boolean,
)

data class ScenarioInfo(
    val scenarioNumber: Int,
    val scenarioRequirements: LogicalCondition,
    val newScenario: List<Int>,
    val pack: PackType,
    val monsters: List<String>
)
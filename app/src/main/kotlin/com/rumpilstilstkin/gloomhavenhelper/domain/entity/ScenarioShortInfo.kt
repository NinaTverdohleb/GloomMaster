package com.rumpilstilstkin.gloomhavenhelper.domain.entity

data class ScenarioShortInfo(
    val scenarioNumber: Int,
    val scenarioName: String,
    val scenarioRequirements: LogicalCondition,
    val isCompleted: Boolean,
    val location: String,
    val pack: PackType,
    val monsters: List<String>
)

data class ScenarioInfo(
    val scenarioNumber: Int,
    val scenarioName: String,
    val scenarioRequirements: LogicalCondition,
    val newScenario: List<Int>,
    val teamAchievements: List<String>,
    val globalAchievements: List<String>,
    val location: String,
    val pack: PackType,
    val monsters: List<String>
)
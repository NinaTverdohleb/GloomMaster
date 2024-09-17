package com.rumpilstilstkin.gloomhavenhelper.domain.entity

data class TeamScenario(
    val scenarioNumber: Int,
    val scenarioName: String,
    val scenarioRequirements: String,
    val isCompleted: Boolean
)

data class ScenarioInfo(
    val scenarioNumber: Int,
    val scenarioName: String,
    val scenarioRequirements: String,
    val newScenario: List<Int>,
    val teamAchievements: List<String>,
    val globalAchievements: List<String>,
)
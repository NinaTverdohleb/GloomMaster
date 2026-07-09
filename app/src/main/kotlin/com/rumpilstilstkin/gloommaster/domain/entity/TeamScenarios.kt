package com.rumpilstilstkin.gloommaster.domain.entity

data class TeamScenarios(
    val activeScenarios: List<ScenarioInfoWithName>,
    val blockedScenarios: List<ScenarioInfoWithName>,
    val completedScenarios: List<ScenarioInfoWithName>,
)

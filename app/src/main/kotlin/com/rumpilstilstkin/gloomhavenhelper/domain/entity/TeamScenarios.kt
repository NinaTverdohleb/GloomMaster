package com.rumpilstilstkin.gloomhavenhelper.domain.entity

data class TeamScenarios(
    val activeScenarios: List<ScenarioInfoWithName>,
    val blockedScenarios: List<ScenarioInfoWithName>,
    val completedScenarios: List<ScenarioInfoWithName>,
)

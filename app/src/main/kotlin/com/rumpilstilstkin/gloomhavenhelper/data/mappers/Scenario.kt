package com.rumpilstilstkin.gloomhavenhelper.data.mappers

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.ScenarioBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TeamScenarioBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.TeamScenario

fun TeamScenarioBd.toDomain() = TeamScenario(
    scenarioNumber = this.scenarioNumber,
    scenarioName = this.scenarioName,
    scenarioRequirements = this.scenarioRequirements,
    isCompleted = this.completed,
    location = this.location
)

fun ScenarioBd.toDomain() = ScenarioInfo(
    scenarioNumber = this.scenarioNumber,
    scenarioName = this.name,
    scenarioRequirements = this.requirements,
    newScenario = this.newScenarios.split(",")
        .mapNotNull { if (it.isNotBlank()) it.trim().toInt() else null },
    teamAchievements = this.teamAchievement.split(","),
    globalAchievements = this.globalAchievement.split(","),
    location = this.location
)
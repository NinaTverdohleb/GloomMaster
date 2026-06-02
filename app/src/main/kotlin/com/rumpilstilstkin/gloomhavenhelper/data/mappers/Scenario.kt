package com.rumpilstilstkin.gloomhavenhelper.data.mappers

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.ScenarioBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.ScenarioWithNameBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.LogicalCondition
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioInfoWithName
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioShortInfo

fun ScenarioWithNameBd.toDomain(
    isCompleted: Boolean = false,
    dictionary: Map<String, String>,
) = ScenarioInfoWithName(
    scenarioNumber = this.scenarioNumber,
    scenarioName = name,
    scenarioRequirements =
        LogicalCondition.createWithDictionary(
            conditions = this.requirements,
            dictionary = dictionary,
        ),
    newScenario =
        this.newScenarios
            .split(",")
            .mapNotNull { if (it.isNotBlank()) it.trim().toInt() else null },
    location = this.locationName,
    pack = PackType.valueOf(this.pack),
    monsters = this.monsters,
    isCompleted = isCompleted,
)

fun ScenarioBd.toShortDomain(isCompleted: Boolean) =
    ScenarioShortInfo(
        scenarioNumber = this.scenarioNumber,
        scenarioRequirements = LogicalCondition(this.requirements),
        pack = PackType.valueOf(this.pack),
        isCompleted = isCompleted,
        monsters = this.monsters,
    )

fun ScenarioBd.toInfoDomain() =
    ScenarioInfo(
        scenarioNumber = this.scenarioNumber,
        scenarioRequirements = LogicalCondition(this.requirements),
        pack = PackType.valueOf(this.pack),
        newScenario =
            this.newScenarios
                .split(",")
                .mapNotNull { if (it.isNotBlank()) it.trim().toInt() else null },
        monsters = this.monsters,
    )

package com.rumpilstilstkin.gloomhavenhelper.data.mappers

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.ScenarioBd
import com.rumpilstilstkin.gloomhavenhelper.data.TextResolver
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.LogicalCondition
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioInfo
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.ScenarioShortInfo
import com.rumpilstilstkin.gloomhavenhelper.localization.TranslationKeys

fun ScenarioBd.toDomain() = ScenarioInfo(
    scenarioNumber = this.scenarioNumber,
    scenarioName = this.name,
    scenarioRequirements = LogicalCondition(this.requirements),
    newScenario = this.newScenarios.split(",")
        .mapNotNull { if (it.isNotBlank()) it.trim().toInt() else null },
    teamAchievements = this.teamAchievement.split(","),
    globalAchievements = this.globalAchievement.split(","),
    location = this.location,
    pack = PackType.valueOf(this.pack),
    monsters = this.monsters
)

fun ScenarioBd.toShortDomain(
    isCompleted: Boolean
) = ScenarioShortInfo(
    scenarioNumber = this.scenarioNumber,
    scenarioName = this.name,
    scenarioRequirements = LogicalCondition(this.requirements),
    location = this.location,
    pack = PackType.valueOf(this.pack),
    isCompleted = isCompleted,
    monsters = this.monsters
)

/**
 * Replaces display-only [ScenarioShortInfo.scenarioName] and [ScenarioShortInfo.location] with
 * text for the active locale, both keyed by scenario number. Logic fields (requirements,
 * achievements, monsters) keep their canonical values, so scenario unlock evaluation is
 * unaffected by language.
 */
fun ScenarioShortInfo.localized(resolver: TextResolver) = copy(
    scenarioName = resolver.resolveScenarioName(scenarioNumber),
    location = resolver.resolveScenarioLocation(scenarioNumber, location),
)

fun ScenarioInfo.localized(resolver: TextResolver) = copy(
    scenarioName = resolver.resolveScenarioName(scenarioNumber),
    location = resolver.resolveScenarioLocation(scenarioNumber, location),
)

private fun TextResolver.resolveScenarioName(scenarioNumber: Int): String =
    resolve(TranslationKeys.SCENARIO, scenarioNumber.toString(), TranslationKeys.FIELD_NAME)

private fun TextResolver.resolveScenarioLocation(scenarioNumber: Int, canonical: String): String =
    if (canonical.isBlank()) canonical
    else resolve(TranslationKeys.SCENARIO, scenarioNumber.toString(), TranslationKeys.FIELD_LOCATION)
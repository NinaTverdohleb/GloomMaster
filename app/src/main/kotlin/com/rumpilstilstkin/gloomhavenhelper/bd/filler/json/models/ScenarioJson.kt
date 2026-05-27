package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.ScenarioBd
import kotlinx.serialization.Serializable

@Serializable
data class ScenarioJson(
    val scenarioNumber: Int,
    val newScenarios: String = "",
    // Achievement references below are stable catalog keys (a "(-)" prefix marks removal). The
    // display name is resolved from the translation store by key.
    val teamAchievement: String = "",
    val globalAchievement: String = "",
    val requirements: String = "",
    // Monster catalog keys; display names resolved from the translation store.
    val monsters: List<String> = emptyList(),
    val pack: String
) {
    fun toEntity() = ScenarioBd(
        scenarioNumber = scenarioNumber,
        newScenarios = newScenarios,
        teamAchievement = teamAchievement,
        globalAchievement = globalAchievement,
        requirements = requirements,
        monsters = monsters,
        pack = pack
    )
}

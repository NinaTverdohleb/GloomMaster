package com.rumpilstilstkin.gloommaster.bd.filler.json.models

import com.rumpilstilstkin.gloommaster.bd.entity.ScenarioBd
import com.rumpilstilstkin.gloommaster.bd.entity.ScenarioTranslationsBd
import kotlinx.serialization.Serializable

@Serializable
data class ScenarioJson(
    val scenarioNumber: Int,
    val newScenarios: String = "",
    val requirements: String = "",
    val monsters: List<String> = emptyList(),
    val location: String = "",
    val pack: String,
) {
    fun toEntity() =
        ScenarioBd(
            scenarioNumber = scenarioNumber,
            newScenarios = newScenarios,
            requirements = requirements,
            monsters = monsters,
            location = location,
            pack = pack,
        )
}

@Serializable
data class ScenarioTranslationJson(
    val scenarioNumber: Int,
    val name: String,
) {
    fun toEntity(locale: String) =
        ScenarioTranslationsBd(
            scenarioNumber = scenarioNumber,
            locale = locale,
            name = name,
        )
}

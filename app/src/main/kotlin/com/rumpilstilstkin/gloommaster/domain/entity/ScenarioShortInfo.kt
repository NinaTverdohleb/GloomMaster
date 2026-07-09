package com.rumpilstilstkin.gloommaster.domain.entity

data class ScenarioShortInfo(
    val scenarioNumber: Int,
    val scenarioRequirements: LogicalCondition,
    val isCompleted: Boolean,
    val pack: PackType,
    val monsters: List<String>,
) {
    companion object {
        fun fixture(
            scenarioNumber: Int = 1,
            isCompleted: Boolean = false,
            scenarioRequirements: LogicalCondition = LogicalCondition(""),
            pack: PackType = PackType.MAIN,
            monster: List<String> = emptyList(),
        ): ScenarioShortInfo =
            ScenarioShortInfo(
                scenarioNumber = scenarioNumber,
                scenarioRequirements = scenarioRequirements,
                isCompleted = isCompleted,
                pack = pack,
                monsters = monster,
            )
    }
}

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
    val monsters: List<String>,
)

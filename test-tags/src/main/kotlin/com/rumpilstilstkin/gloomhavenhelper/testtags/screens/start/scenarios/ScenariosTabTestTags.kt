package com.rumpilstilstkin.gloomhavenhelper.testtags.screens.start.scenarios

object ScenariosTabTestTags {
    private const val SECTION_PREFIX = "ScenariosTabSection_"
    private const val SCENARIO_CARD_PREFIX = "ScenariosTabScenarioCard_"
    const val ADD_FAB = "ScenariosTabAddFab"

    fun scenarioCard(scenarioNumber: Int) = "$SCENARIO_CARD_PREFIX$scenarioNumber"

    fun section(sectionName: String) = "$SECTION_PREFIX$sectionName"
}

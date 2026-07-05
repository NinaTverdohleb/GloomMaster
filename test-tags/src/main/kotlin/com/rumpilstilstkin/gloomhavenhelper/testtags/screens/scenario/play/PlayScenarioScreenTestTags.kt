package com.rumpilstilstkin.gloomhavenhelper.testtags.screens.scenario.play

object PlayScenarioScreenTestTags {
    private const val MONSTER_CARD_PREFIX = "ScenarioScreenMonsterCard"
    const val ADD_MONSTER_FAB = "ScenarioScreenAddMonsterFab"
    const val ADD_MONSTER_FAB_SCENARIO = "ScenarioScreenAddMonsterFabScenario"
    const val ADD_MONSTER_FAB_ALL = "ScenarioScreenAddMonsterFabAll"
    const val ROUND_BUTTON = "ScenarioScreenRoundButton"
    const val MONSTER_PAGER = "ScenarioScreenMonsterPager"

    fun card(index: Int) = "$MONSTER_CARD_PREFIX$index"
}

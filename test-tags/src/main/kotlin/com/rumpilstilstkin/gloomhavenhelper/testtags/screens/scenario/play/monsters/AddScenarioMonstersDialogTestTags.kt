package com.rumpilstilstkin.gloomhavenhelper.testtags.screens.scenario.play.monsters

object AddScenarioMonstersDialogTestTags {
    private const val MONSTER_PREFIX = "AddScenarioMonstersDialogMonsterItem"
    const val ADD_BUTTON = "AddScenarioMonstersDialogAddButton"

    fun monster(index: Int) = "$MONSTER_PREFIX$index"
}

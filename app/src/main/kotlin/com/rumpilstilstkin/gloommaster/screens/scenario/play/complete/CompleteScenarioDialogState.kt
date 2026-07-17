package com.rumpilstilstkin.gloommaster.screens.scenario.play.complete

data class CompleteScenarioDialogInput(
    val level: Int,
    val exp: Int,
    val gold: Int,
    val trapDamage: Int,
    val scenarioName: String,
    val scenarioNumber: Int? = null,
    val location: String? = null,
)

sealed interface CompleteScenarioDialogAction {
    data object Complete : CompleteScenarioDialogAction
}

data object CompleteScenarioDialogComplete

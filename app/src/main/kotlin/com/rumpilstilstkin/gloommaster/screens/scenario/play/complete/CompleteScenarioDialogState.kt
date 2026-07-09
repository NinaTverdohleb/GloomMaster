package com.rumpilstilstkin.gloommaster.screens.scenario.play.complete

data class CompleteScenarioDialogInput(
    val exp: Int,
    val gold: Int,
)

sealed interface CompleteScenarioDialogAction {
    data object Complete : CompleteScenarioDialogAction
}

data object CompleteScenarioDialogComplete

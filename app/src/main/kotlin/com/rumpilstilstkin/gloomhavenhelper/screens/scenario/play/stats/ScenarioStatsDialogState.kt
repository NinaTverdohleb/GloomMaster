package com.rumpilstilstkin.gloomhavenhelper.screens.scenario.play.stats

data class ScenarioStatsDialogInput(
    val level: Int,
    val exp: Int,
    val gold: Int,
    val trapDamage: Int,
    val scenarioName: String,
    val scenarioNumber: Int? = null,
    val location: String? = null,
)

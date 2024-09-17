package com.rumpilstilstkin.gloomhavenhelper.screens.models

data class TeamUI(
    val teamId: Int,
    val teamLevel: Int,
    val teamName: String,
    val teamReputation: Int,
    val prosperity: Int,
    val teamAchievements: String,
    val globalAchievements: String,
    val teamScenario: List<ShortScenarioUI>,
    val characters: List<CharacterUI>,
    val canAddCharacter: Boolean = false,
)
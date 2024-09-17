package com.rumpilstilstkin.gloomhavenhelper.domain.entity

data class TeamInfo(
    val id: Int,
    val name: String,
    val level: Int,
    val teamAchievement: String,
    val globalAchievement: String,
    val reputation: Int,
    val prosperity: Int,
    val scenario: List<TeamScenario>,
    val characters: List<CharacterInfo>
)
data class ShortTeamInfo(
    val teamId: Int,
    val name: String,
    val teamAchievement: List<String>,
    val globalAchievement: List<String>,
    val reputation: Int,
    val prosperity: Int,
)

data class TeamInfoWithScenario(
    val teamId: Int,
    val name: String,
    val teamAchievement: String,
    val globalAchievement: String,
    val reputation: Int,
    val prosperity: Int,
    val scenario: List<TeamScenario>
)

data class TeamInfoForSave(
    val name: String,
    val characters: List<CharacterForSave>
)



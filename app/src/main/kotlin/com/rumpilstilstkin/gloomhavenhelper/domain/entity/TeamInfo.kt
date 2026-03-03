package com.rumpilstilstkin.gloomhavenhelper.domain.entity

import kotlin.Int

data class TeamInfo(
    val id: Int,
    val name: String,
    val level: Int,
    val teamAchievement: String,
    val globalAchievement: String,
    val reputation: Int,
    val scenario: List<TeamScenario>,
    val characters: List<CharacterInfo>,
    val shopDiscount: Int,
    val prosperity: Prosperity,
)

data class Prosperity(
    val prosperitySource: Int,
    val prosperityLevelValue: Int,
    val prosperityLevel: Int,
    val prosperityRange: IntRange,
) {
    val isStartValue = prosperityLevel == 0 && prosperityLevelValue == 0
    val isMax = prosperityLevel == 9
    companion object {
        fun fixture() = Prosperity(
            prosperityLevelValue = 2,
            prosperityLevel = 3,
            prosperityRange = IntRange(0, 5),
            prosperitySource = 11
        )
    }
}

data class ShortTeamInfo(
    val teamId: Int,
    val name: String,
    val teamAchievement: List<String>,
    val globalAchievement: List<String>,
    val reputation: Int,
    val prosperity: Int,
) {
    companion object {
        fun fixture(
            teamId: Int = 1,
            teamName: String = "Name"
        ) = ShortTeamInfo(
            teamId = teamId,
            name = teamName,
            teamAchievement = listOf("Achievement 1", "Achievement 2"),
            globalAchievement = listOf("Achievement 3", "Achievement 4"),
            reputation = 10,
            prosperity = 20
        )
    }
}

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



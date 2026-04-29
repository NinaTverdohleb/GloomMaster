package com.rumpilstilstkin.gloomhavenhelper.domain.entity

import kotlinx.serialization.Serializable

data class TeamInfo(
    val id: Int,
    val name: String,
    val level: Int,
    val teamAchievement: List<Achievement>,
    val globalAchievement: List<Achievement>,
    val reputation: Int,
    val activeScenario: List<ScenarioShortInfo>,
    val aliveCharacters: List<CharacterInfo>,
    val shopDiscount: Int,
    val prosperity: Prosperity,
    val packs: List<PackType>,
    val hasActiveScenario: Boolean,
    val churchValue: Int,
)

data class Prosperity(
    val prosperitySource: Int,
    val prosperityLevelValue: Int,
    val prosperityLevel: Int,
    val prosperityRange: IntRange,
) {
    val isStartValue = prosperityLevel == 1 && prosperityLevelValue == 0
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

@Serializable
data class Team(
    val teamId: Int,
    val name: String,
    val packs: List<PackType>
)

@Serializable
data class ShortTeamInfo(
    val teamId: Int,
    val name: String,
    val teamAchievement: List<Achievement>,
    val globalAchievement: List<Achievement>,
    val aliveCharacterIds: List<Int>,
    val reputation: Int,
    val prosperity: Int,
    val packs: List<PackType>,
    val churchValue: Int,
) {
    companion object {
        fun fixture(
            teamId: Int = 1,
            teamName: String = "Name"
        ) = ShortTeamInfo(
            teamId = teamId,
            name = teamName,
            teamAchievement = listOf(Achievement.fixture("Achievement 1"), Achievement.fixture("Achievement 2")),
            globalAchievement = listOf(Achievement.fixture("Achievement 1"), Achievement.fixture("Achievement 2")),
            reputation = 10,
            prosperity = 20,
            packs = listOf(PackType.MAIN),
            aliveCharacterIds = listOf(1, 2, 3),
            churchValue = 100
        )
    }
}

data class TeamInfoWithScenario(
    val teamId: Int,
    val name: String,
    val teamAchievement: List<Achievement> ,
    val globalAchievement: List<Achievement> ,
    val reputation: Int,
    val prosperity: Int,
    val scenario: List<ScenarioShortInfo>,
    val packs: List<PackType>,
    val churchValue: Int,
)

data class TeamInfoForSave(
    val name: String,
    val characters: List<CharacterForSave> = emptyList(),
    val packs: List<PackType>
)



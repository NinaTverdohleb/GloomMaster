package com.rumpilstilstkin.gloomhavenhelper.domain.entity

import kotlinx.serialization.Serializable

data class TeamInfo(
    val id: Int,
    val name: String,
    val level: Int,
    val teamAchievement: List<AchievementWithName>,
    val globalAchievement: List<AchievementWithName>,
    val reputation: Int,
    val activeScenario: List<ScenarioInfoWithName>,
    val aliveCharacters: List<CharacterInfo>,
    val shopDiscount: Int,
    val prosperity: Prosperity,
    val packs: List<PackType>,
    val hasActiveScenario: Boolean,
    val churchValue: Int,
    val difficultyLevel: DifficultyLevel = DifficultyLevel.NORMAL,
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
        fun fixture() =
            Prosperity(
                prosperityLevelValue = 2,
                prosperityLevel = 3,
                prosperityRange = IntRange(0, 5),
                prosperitySource = 11,
            )
    }
}

@Serializable
data class Team(
    val teamId: Int,
    val name: String,
    val packs: List<PackType>,
    val difficultyLevel: DifficultyLevel = DifficultyLevel.NORMAL,
)

@Serializable
data class ShortTeamInfoWithTranslations(
    val teamId: Int,
    val name: String,
    val achievements: List<AchievementWithName>,
    val aliveCharacterIds: List<Int>,
    val reputation: Int,
    val prosperity: Int,
    val packs: List<PackType>,
    val churchValue: Int,
    val difficultyLevel: DifficultyLevel = DifficultyLevel.NORMAL,
) {
    companion object {
        fun fixture(
            teamId: Int = 1,
            teamName: String = "Name",
        ) = ShortTeamInfoWithTranslations(
            teamId = teamId,
            name = teamName,
            achievements = listOf(
                AchievementWithName.fixture("Achievement 1"),
                AchievementWithName.fixture("Achievement 2")
            ),
            reputation = 10,
            prosperity = 20,
            packs = listOf(PackType.MAIN),
            aliveCharacterIds = listOf(1, 2, 3),
            churchValue = 100,
            difficultyLevel = DifficultyLevel.NORMAL,
        )
    }
}

@Serializable
data class ShortTeamInfo(
    val teamId: Int,
    val name: String,
    val achievements: List<Achievement>,
    val aliveCharacterIds: List<Int>,
    val reputation: Int,
    val prosperity: Int,
    val packs: List<PackType>,
    val churchValue: Int,
    val difficultyLevel: DifficultyLevel = DifficultyLevel.NORMAL,
    val countRetiredCharacters: Int,
) {
    companion object {
        fun fixture(
            teamId: Int = 1,
            teamName: String = "Name",
        ) = ShortTeamInfo(
            teamId = teamId,
            name = teamName,
            achievements = listOf(
                Achievement.fixture("Achievement 1"),
                Achievement.fixture("Achievement 2")
            ),
            reputation = 10,
            prosperity = 20,
            packs = listOf(PackType.MAIN),
            aliveCharacterIds = listOf(1, 2, 3),
            churchValue = 100,
            difficultyLevel = DifficultyLevel.NORMAL,
            countRetiredCharacters = 0,
        )
    }
}

data class TeamInfoForSave(
    val name: String,
    val characters: List<CharacterForSave> = emptyList(),
    val packs: List<PackType>,
    val difficultyLevel: DifficultyLevel = DifficultyLevel.NORMAL,
) {
    companion object {
        fun fixture(
            name: String = "TEEEEEEM",
            characters: List<CharacterForSave> = emptyList(),
            packs: List<PackType> = listOf(PackType.MAIN),
            difficultyLevel: DifficultyLevel = DifficultyLevel.NORMAL,
        ) = TeamInfoForSave(
            name = name,
            characters = characters,
            packs = packs,
            difficultyLevel = difficultyLevel,
        )
    }
}

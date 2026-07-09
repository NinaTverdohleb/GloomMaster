package com.rumpilstilstkin.gloommaster.domain.entity

import com.rumpilstilstkin.gloommaster.domain.entity.quest.CharacterPersonalQuest
import kotlinx.serialization.Serializable
import kotlin.math.ceil

data class CharacterForSave(
    val name: String,
    val level: Int,
    val characterType: CharacterClassType,
    val experience: Int,
    val teamId: Int? = null,
    val goldCount: Int? = null,
    val isAlive: Boolean = true,
    val notes: String = "",
    val checkMarkCount: Int = 0,
    val additionalContOfPerks: Int = 0,
) {
    companion object {
        fun fixture(
            name: String = "Brute",
            level: Int = 1,
            characterType: CharacterClassType = CharacterClassType.Brute,
            experience: Int = 0,
            teamId: Int? = 1,
            goldCount: Int? = null,
            isAlive: Boolean = true,
            notes: String = "",
            checkMarkCount: Int = 0,
            additionalContOfPerks: Int = 0,
        ): CharacterForSave =
            CharacterForSave(
                name = name,
                level = level,
                characterType = characterType,
                experience = experience,
                teamId = teamId,
                goldCount = goldCount,
                isAlive = isAlive,
                notes = notes,
                checkMarkCount = checkMarkCount,
                additionalContOfPerks = additionalContOfPerks,
            )
    }
}

@Serializable
data class CharacterInfo(
    val name: String,
    val level: Int,
    val characterType: CharacterClassType,
    val isAlive: Boolean,
    val id: Int,
    val team: Team?,
    val experience: Int,
    val goldCount: Int,
    val checkMarkCount: Int,
    val notes: String,
    val additionalContOfPerks: Int,
)

data class CharacterShortInfo(
    val name: String,
    val level: Int,
    val characterType: CharacterClassType,
    val isAlive: Boolean,
    val id: Int,
    val teamId: Int?,
    val experience: Int,
    val goldCount: Int,
    val checkMarkCount: Int,
    val notes: String,
)

data class CharacterFullInfo(
    val generalInfo: CharacterInfo,
    val nextLevelExperience: Int,
    val isDonateAvailable: Boolean,
    val personalQuest: CharacterPersonalQuest?,
)

fun List<Int>.toLevel(difficultyLevel: DifficultyLevel): Int {
    val clearLevel = ceil(this.average().div(2)).toInt() + difficultyLevel.value
    return clearLevel.coerceIn(0, MAX_LEVEL)
}

private const val MAX_LEVEL = 7

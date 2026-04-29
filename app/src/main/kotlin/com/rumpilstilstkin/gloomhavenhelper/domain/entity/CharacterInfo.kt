package com.rumpilstilstkin.gloomhavenhelper.domain.entity

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterPersonalQuest
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
)

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
    val notes: String
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
    val notes: String
)

data class CharacterFullInfo(
    val generalInfo: CharacterInfo,
    val nextLevelExperience: Int,
    val isDonateAvailable: Boolean,
    val personalQuest: CharacterPersonalQuest?,
)

fun List<Int>.toLevel(): Int {
    val clearLevel = ceil(this.average().div(2)).toInt()
    return if (clearLevel > MAX_LEVEL) MAX_LEVEL else clearLevel
}

private const val MAX_LEVEL = 7

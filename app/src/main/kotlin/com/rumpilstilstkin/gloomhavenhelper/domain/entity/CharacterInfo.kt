package com.rumpilstilstkin.gloomhavenhelper.domain.entity

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterPersonalQuest

data class CharacterForSave(
    val name: String,
    val level: Int,
    val characterType: CharacterClassType
)

data class CharacterInfo(
    val name: String,
    val level: Int,
    val characterClass: CharacterClass,
    val isAlive: Boolean,
    val id: Int,
    val team: ShortTeamInfo?,
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
    val clearLevel = this.average().div(2)
    val level = clearLevel.toInt() + if(clearLevel - clearLevel.toInt() > 0) 1 else 0
    return if (level > MAX_LEVEL) MAX_LEVEL else level
}

private const val MAX_LEVEL = 7

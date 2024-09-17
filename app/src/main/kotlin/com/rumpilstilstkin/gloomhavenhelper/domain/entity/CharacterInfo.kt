package com.rumpilstilstkin.gloomhavenhelper.domain.entity

import kotlin.math.roundToInt

data class CharacterClass(
    val id: Int,
    val image: Int,
    val name: String
)

data class CharacterForSave(
    val name: String,
    val level: Int,
    val classId: Int
)

data class CharacterInfo(
    val name: String,
    val level: Int,
    val characterClass: CharacterClass,
    val isAlive: Boolean,
    val id: Int
)

fun List<Int>.toLevel(): Int {
    val clearLevel = this.average().div(2)
    val level = clearLevel.toInt() + if(clearLevel - clearLevel.toInt() > 0) 1 else 0
    return if (level > MAX_LEVEL) MAX_LEVEL else level
}

private const val MAX_LEVEL = 7



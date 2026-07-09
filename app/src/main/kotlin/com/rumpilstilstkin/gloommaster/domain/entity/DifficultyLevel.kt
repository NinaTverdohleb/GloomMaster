package com.rumpilstilstkin.gloommaster.domain.entity

import kotlinx.serialization.Serializable

@Serializable
enum class DifficultyLevel(
    val value: Int,
) {
    EASY(-1),
    NORMAL(0),
    HARD(1),
    VERY_HARD(2),
    ;

    companion object {
        fun fromValue(value: Int): DifficultyLevel = entries.find { it.value == value } ?: NORMAL
    }
}

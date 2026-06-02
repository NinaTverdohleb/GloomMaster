package com.rumpilstilstkin.gloomhavenhelper.domain.entity

import kotlinx.serialization.Serializable
import kotlin.String

@Serializable
data class Achievement(
    val slug: String,
    val value: Int,
    val maxValue: Int,
    val isGlobal: Boolean,
) {
    companion object {
        fun fixture(
            slug: String = "Achievement 1",
            value: Int = 1,
            maxValue: Int = 1,
            isGlobal: Boolean = false,
        ) = Achievement(
            slug = slug,
            value = value,
            maxValue = maxValue,
            isGlobal = isGlobal,
        )
    }

    fun toAchievementWithName(name: String?) =
        AchievementWithName(
            slug = slug,
            value = value,
            maxValue = maxValue,
            name = name ?: "",
            isGlobal = isGlobal,
        )
}

@Serializable
data class AchievementWithName(
    val slug: String,
    val name: String,
    val value: Int,
    val maxValue: Int,
    val isGlobal: Boolean,
) {
    fun toAchievement() =
        Achievement(
            slug = slug,
            value = value,
            maxValue = maxValue,
            isGlobal = isGlobal,
        )

    companion object {
        fun fixture(
            name: String = "",
            slug: String = name,
            value: Int = 1,
            maxValue: Int = 1,
            isGlobal: Boolean = false,
        ) = AchievementWithName(
            slug = slug,
            value = value,
            maxValue = maxValue,
            name = name,
            isGlobal = isGlobal,
        )
    }
}

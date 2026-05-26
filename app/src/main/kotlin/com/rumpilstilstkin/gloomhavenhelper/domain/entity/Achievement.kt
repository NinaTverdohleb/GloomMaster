package com.rumpilstilstkin.gloomhavenhelper.domain.entity

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Achievement(
    val name: String,
    val value: Int,
    val maxValue: Int,
    // Canonical [name] is the identity: it is persisted on the team and matched by scenario
    // unlock logic, so it must never change with language. [displayName] is the display-only
    // localized text, defaults to [name], and is @Transient so it is never serialized into a
    // saved team (keeping stored data and unlock matching language-independent).
    @Transient val displayName: String = name,
){
    companion object {
        fun fixture(
            name: String = "Achievement 1",
            value: Int = 1,
            maxValue: Int = 1,
        ) = Achievement(
            name = name,
            value = value,
            maxValue = maxValue
        )
    }
}

package com.rumpilstilstkin.gloomhavenhelper.domain.entity

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Achievement(
    val key: String,
    val value: Int,
    val maxValue: Int,
    // [key] is the stable catalog identity: it is persisted on the team and matched by scenario
    // unlock logic, so it is language-independent. [displayName] is the display-only localized
    // text, defaults to [key], and is @Transient so it is never serialized into a saved team
    // (keeping stored data and unlock matching language-independent).
    @Transient val displayName: String = key,
){
    companion object {
        fun fixture(
            key: String = "1",
            value: Int = 1,
            maxValue: Int = 1,
        ) = Achievement(
            key = key,
            value = value,
            maxValue = maxValue
        )
    }
}

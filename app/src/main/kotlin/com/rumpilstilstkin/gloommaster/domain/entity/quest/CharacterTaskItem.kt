package com.rumpilstilstkin.gloommaster.domain.entity.quest

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("taskType")
sealed interface CharacterTaskItem {
    val id: Int
    val completed: Boolean
    val priority: Int
    val text: String

    @Serializable
    @SerialName("Check")
    data class Check(
        override val id: Int,
        override val priority: Int,
        override val text: String = "",
        val isChecked: Boolean = false,
    ) : CharacterTaskItem {
        override val completed: Boolean
            get() = isChecked
    }

    @Serializable
    @SerialName("Count")
    data class Count(
        override val id: Int,
        override val priority: Int,
        override val text: String = "",
        val count: Int = 0,
        val currentCount: Int = 0,
        val step: Int = 1,
    ) : CharacterTaskItem {
        override val completed: Boolean
            get() = count <= currentCount
    }
}

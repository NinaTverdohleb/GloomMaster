package com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("kind")
sealed interface MonsterAction {
    @Serializable
    @SerialName("action")
    data class Action(
        val statType: MonsterStatType,
        val modifier: String,
        val subAction: List<MonsterAction>? = null,
    ) : MonsterAction

    @Serializable
    @SerialName("text")
    data class Text(
        val content: String,
        val subAction: List<MonsterAction>? = null,
    ) : MonsterAction
}

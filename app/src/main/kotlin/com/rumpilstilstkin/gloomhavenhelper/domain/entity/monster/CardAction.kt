package com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface CardAction {

    @Serializable
    @SerialName("action")
    data class Action(
        val type: MonsterStatType,
        val modifier: String,
        val subAction: CardAction? = null,
    ) : CardAction

    @Serializable
    @SerialName("text")
    data class Text(
        val content: String,
    ) : CardAction

    @Serializable
    @SerialName("special")
    data class Special(
        val key: String,
        val subAction: CardAction? = null,
    ) : CardAction
}

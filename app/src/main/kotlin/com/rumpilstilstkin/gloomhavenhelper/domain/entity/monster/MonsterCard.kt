package com.rumpilstilstkin.gloomhavenhelper.domain.entity.monster

import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

@Serializable
data class MonsterCard(
    val deckName: String,
    val cardId: Int,
    val actions: List<MonsterCardAction>,
    val needsShuffle: Boolean = false,
    val initiative: Int,
)

@Serializable
data class MonsterCardAction(
    val text: String,
    val startEffect: String? = null,
    val endEffect: String? = null,
    val subAction: List<MonsterCardAction> = emptyList(),
)

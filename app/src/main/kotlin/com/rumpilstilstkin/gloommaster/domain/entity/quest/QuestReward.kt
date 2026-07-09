package com.rumpilstilstkin.gloommaster.domain.entity.quest

import com.rumpilstilstkin.gloommaster.domain.entity.CharacterClassType
import kotlinx.serialization.Serializable

@Serializable
data class QuestReward(
    val classType: CharacterClassType? = null,
    val alternativeReward: String = "",
)

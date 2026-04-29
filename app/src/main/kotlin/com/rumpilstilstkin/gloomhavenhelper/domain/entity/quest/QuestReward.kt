package com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType
import kotlinx.serialization.Serializable

@Serializable
data class QuestReward(
    val classType: CharacterClassType? = null,
    val alternativeReward: String = ""
)

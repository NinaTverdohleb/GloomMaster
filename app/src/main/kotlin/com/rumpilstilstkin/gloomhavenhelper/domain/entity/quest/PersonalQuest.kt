package com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest

import kotlinx.serialization.Serializable

@Serializable
data class CharacterPersonalQuest(
    val questId: String,
    val title: String,
    val descriptions: String,
    val reward: QuestReward,
    val tasks: List<CharacterTaskItem>,
)

@Serializable
data class CharacterPersonalQuestShort(
    val questId: String,
    val tasks: List<CharacterTaskItem>,
)

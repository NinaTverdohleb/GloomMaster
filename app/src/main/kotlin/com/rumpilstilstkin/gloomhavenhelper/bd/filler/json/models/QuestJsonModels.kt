package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json.models

import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterTaskItem
import kotlinx.serialization.Serializable

@Serializable
data class PersonalQuestJson(
    val questId: String,
    val title: String,
    val description: String,
    val specialText: String = "",
    val characterType: String? = null,
    val pack: String = "MAIN",
    val tasks: List<CharacterTaskItem>
)

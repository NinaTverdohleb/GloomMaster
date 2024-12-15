package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterTaskItem

@Entity
data class PersonalQuestBd(
    @PrimaryKey
    val questId: String,
    val title: String,
    val description: String,
    val specialText: String,
    val characterType: String?,
    val tasks: List<CharacterTaskItem>,
)
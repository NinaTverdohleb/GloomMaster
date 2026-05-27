package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterTaskItem

@Entity
data class PersonalQuestBd(
    @PrimaryKey
    val questId: String,
    val specialText: String,
    val characterType: String?,
    val tasks: List<CharacterTaskItem>,
    val pack: String = PackType.MAIN.name,
)
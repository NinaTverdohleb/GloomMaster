package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.PackType
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterTaskItem

@Entity
data class PersonalQuestBd(
    @PrimaryKey
    val questId: String,
    val characterType: String?,
    val tasks: List<CharacterTaskItem>,
    val pack: String = PackType.MAIN.name,
)

@Entity(
    primaryKeys = ["questId", "locale"],
    foreignKeys = [
        ForeignKey(
            entity = PersonalQuestBd::class,
            parentColumns = ["questId"],
            childColumns = ["questId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PersonalQuestTranslationsBd(
    val questId: String,
    val locale: String,
    val title: String,
    val description: String,
    val specialText: String,
)

@Entity(
    primaryKeys = ["questId", "locale", "taskId"],
    foreignKeys = [
        ForeignKey(
            entity = PersonalQuestBd::class,
            parentColumns = ["questId"],
            childColumns = ["questId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PersonalQuestTaskTranslationsBd(
    val questId: String,
    val locale: String,
    val text: String,
    val taskId: Int,
)
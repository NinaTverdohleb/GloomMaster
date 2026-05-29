package com.rumpilstilstkin.gloomhavenhelper.bd.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterTaskItem

@Entity(foreignKeys = [
    ForeignKey(
        entity = CharacterBd::class,
        parentColumns = arrayOf("characterId"),
        childColumns = arrayOf("characterId"),
        onDelete = CASCADE
    ),
    ForeignKey(
        entity = PersonalQuestBd::class,
        parentColumns = arrayOf("questId"),
        childColumns = arrayOf("questId"),
        onDelete = CASCADE
    )
],
    indices = [
        Index("characterId"),
        Index("questId"),
    ]
)
data class CharacterPersonalQuestBd(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val characterId: Int,
    val questId: String,
    val tasks: List<CharacterTaskItem>
)

data class CharacterPersonalQuestDetailsBd(
    @Embedded val characterPersonalQuest: CharacterPersonalQuestBd,
    @Relation(
        parentColumn = "questId",
        entityColumn = "questId"
    )
    val personalTask: PersonalQuestBd
)

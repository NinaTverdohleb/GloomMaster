package com.rumpilstilstkin.gloomhavenhelper.data.mappers

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterPersonalQuestDetailsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.PersonalQuestBd
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterPersonalQuest
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.QuestReward

fun CharacterPersonalQuestDetailsBd.toDomain() =
    this.personalTask.toDomain().copy(
        tasks = this.characterPersonalTask.tasks
    )

fun PersonalQuestBd.toDomain() = CharacterPersonalQuest(
    questId = this.questId,
    title = this.title,
    descriptions = this.description,
    tasks = this.tasks,
    reward = QuestReward(
        classType = this.characterType?.let { com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType.valueOf(it) },
        alternativeReward = this.specialText
    )
)
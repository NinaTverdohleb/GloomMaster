package com.rumpilstilstkin.gloommaster.bd.filler.json.models

import com.rumpilstilstkin.gloommaster.bd.entity.PersonalQuestBd
import com.rumpilstilstkin.gloommaster.bd.entity.PersonalQuestTaskTranslationsBd
import com.rumpilstilstkin.gloommaster.bd.entity.PersonalQuestTranslationsBd
import com.rumpilstilstkin.gloommaster.domain.entity.quest.CharacterTaskItem
import kotlinx.serialization.Serializable

@Serializable
data class PersonalQuestJson(
    val questId: String,
    val characterType: String? = null,
    val pack: String = "MAIN",
    val tasks: List<CharacterTaskItem>,
) {
    fun toEntity() =
        PersonalQuestBd(
            questId = questId,
            characterType = characterType,
            tasks = tasks,
            pack = pack,
        )
}

@Serializable
data class PersonalQuestTranslationJson(
    val questId: String,
    val title: String,
    val description: String,
    val specialText: String = "",
    val taskTexts: List<QuestTaskTranslationJson>,
) {
    fun toEntity(locale: String) =
        PersonalQuestTranslationsBd(
            questId = questId,
            locale = locale,
            title = title,
            description = description,
            specialText = specialText,
        )
}

@Serializable
data class QuestTaskTranslationJson(
    val id: Int,
    val text: String,
) {
    fun toEntity(
        questId: String,
        locale: String,
    ) = PersonalQuestTaskTranslationsBd(
        questId = questId,
        locale = locale,
        text = text,
        taskId = id,
    )
}

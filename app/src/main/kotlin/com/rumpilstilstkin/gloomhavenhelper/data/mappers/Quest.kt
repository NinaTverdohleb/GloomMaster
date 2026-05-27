package com.rumpilstilstkin.gloomhavenhelper.data.mappers

import com.rumpilstilstkin.gloomhavenhelper.bd.entity.CharacterPersonalQuestDetailsBd
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.PersonalQuestBd
import com.rumpilstilstkin.gloomhavenhelper.data.TextResolver
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.CharacterPersonalQuest
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.QuestReward
import com.rumpilstilstkin.gloomhavenhelper.domain.entity.quest.withText
import com.rumpilstilstkin.gloomhavenhelper.localization.TranslationKeys

fun CharacterPersonalQuestDetailsBd.toDomain() =
    this.personalTask.toDomain().copy(
        tasks = this.characterPersonalTask.tasks
    )

// title/descriptions are display-only and always populated by [localized]; they start blank
// because the canonical quest title/description no longer live in the catalog.
fun PersonalQuestBd.toDomain() = CharacterPersonalQuest(
    questId = this.questId,
    title = "",
    descriptions = "",
    tasks = this.tasks,
    reward = QuestReward(
        classType = this.characterType?.let { com.rumpilstilstkin.gloomhavenhelper.domain.entity.CharacterClassType.valueOf(it) },
        alternativeReward = this.specialText
    )
)

/**
 * Replaces display-only quest text (title, description, special reward note, and each task's
 * text) with values for the active locale, keyed by the stable [CharacterPersonalQuest.questId]
 * and task id. Identity/progress fields (questId, reward class, task ids, counts, completion)
 * keep their canonical values, so the assigned quest and task progress are unaffected by
 * language. Only used on display reads — the save/export paths keep canonical (Russian) text.
 */
fun CharacterPersonalQuest.localized(resolver: TextResolver) = copy(
    title = resolver.resolve(TranslationKeys.QUEST, questId, TranslationKeys.FIELD_TITLE),
    descriptions = resolver.resolve(TranslationKeys.QUEST, questId, TranslationKeys.FIELD_DESCRIPTION),
    reward = reward.copy(
        alternativeReward = reward.alternativeReward.takeIf { it.isBlank() }
            ?: resolver.resolve(TranslationKeys.QUEST, questId, TranslationKeys.FIELD_SPECIAL)
    ),
    tasks = tasks.map { task ->
        task.withText(resolver.resolve(TranslationKeys.QUEST_TASK, "$questId:${task.id}", TranslationKeys.FIELD_TEXT))
    }
)
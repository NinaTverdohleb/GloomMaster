package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TranslationDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TranslationBd
import com.rumpilstilstkin.gloomhavenhelper.localization.SupportedLanguages
import com.rumpilstilstkin.gloomhavenhelper.localization.TranslationKeys
import javax.inject.Inject

/**
 * Seeds the translation store for every supported language from per-locale dictionary assets.
 * Scenario name/location are keyed by scenario number, good names by item number, quest fields
 * by quest id (task texts by "$questId:$taskId"), and perk text by perk id — all stable ids.
 * Insert uses REPLACE, so re-running is idempotent.
 */
class TranslationJsonFiller @Inject constructor(
    private val jsonDataLoader: JsonDataLoader,
    private val translationDao: TranslationDao,
) {
    suspend fun fill() {
        translationDao.deleteAll()
        SupportedLanguages.contentLocales.forEach { locale ->
            val rows = buildList {
                jsonDataLoader.loadScenarioTranslations(locale).forEach { (number, text) ->
                    add(translation(TranslationKeys.SCENARIO, number, TranslationKeys.FIELD_NAME, locale, text.name))
                    if (text.location.isNotBlank()) {
                        add(translation(TranslationKeys.SCENARIO, number, TranslationKeys.FIELD_LOCATION, locale, text.location))
                    }
                }
                jsonDataLoader.loadGoodTranslations(locale).forEach { (number, name) ->
                    add(translation(TranslationKeys.GOOD, number, TranslationKeys.FIELD_NAME, locale, name))
                }
                jsonDataLoader.loadQuestTranslations(locale).forEach { (questId, quest) ->
                    add(translation(TranslationKeys.QUEST, questId, TranslationKeys.FIELD_TITLE, locale, quest.title))
                    if (quest.description.isNotBlank()) {
                        add(translation(TranslationKeys.QUEST, questId, TranslationKeys.FIELD_DESCRIPTION, locale, quest.description))
                    }
                    if (quest.special.isNotBlank()) {
                        add(translation(TranslationKeys.QUEST, questId, TranslationKeys.FIELD_SPECIAL, locale, quest.special))
                    }
                    quest.tasks.forEach { (taskId, text) ->
                        add(translation(TranslationKeys.QUEST_TASK, "$questId:$taskId", TranslationKeys.FIELD_TEXT, locale, text))
                    }
                }
                jsonDataLoader.loadPerkTranslations(locale).forEach { (perkId, text) ->
                    add(translation(TranslationKeys.PERK, perkId, TranslationKeys.FIELD_TEXT, locale, text))
                }
            }
            translationDao.insertAll(*rows.toTypedArray())
        }
    }

    private fun translation(entityType: String, entityKey: String, field: String, locale: String, text: String) =
        TranslationBd(
            entityType = entityType,
            entityKey = entityKey,
            fieldName = field,
            locale = locale,
            text = text,
        )
}

package com.rumpilstilstkin.gloomhavenhelper.bd.filler.json

import com.rumpilstilstkin.gloomhavenhelper.bd.dao.TranslationDao
import com.rumpilstilstkin.gloomhavenhelper.bd.entity.TranslationBd
import com.rumpilstilstkin.gloomhavenhelper.localization.SupportedLanguages
import com.rumpilstilstkin.gloomhavenhelper.localization.TranslationKeys
import javax.inject.Inject

/**
 * Seeds the translation store for every supported language from per-locale dictionary assets.
 * Scenario name and location are both keyed by the (stable) scenario number. Insert uses
 * REPLACE, so re-running is idempotent.
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
                    add(translation(number, TranslationKeys.FIELD_NAME, locale, text.name))
                    if (text.location.isNotBlank()) {
                        add(translation(number, TranslationKeys.FIELD_LOCATION, locale, text.location))
                    }
                }
            }
            translationDao.insertAll(*rows.toTypedArray())
        }
    }

    private fun translation(number: String, field: String, locale: String, text: String) =
        TranslationBd(
            entityType = TranslationKeys.SCENARIO,
            entityKey = number,
            fieldName = field,
            locale = locale,
            text = text,
        )
}
